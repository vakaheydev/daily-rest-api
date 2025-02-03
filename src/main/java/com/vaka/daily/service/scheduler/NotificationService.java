package com.vaka.daily.service.scheduler;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.TaskNotification;
import com.vaka.daily.exception.UserNotFoundException;
import com.vaka.daily.service.domain.TaskNotificationService;
import com.vaka.daily.service.scheduler.format.FormatTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vaka.daily.domain.util.DateTimeUtil.*;
import static com.vaka.daily.domain.util.TaskUtil.*;

@Service
@Slf4j
public class NotificationService {
    @Value("${telegram.enabled}")
    private boolean telegramEnabled;
    private final TelegramService telegramService;
    private final com.vaka.daily.service.scheduler.TaskNotificationService notificationService;
    private final FormatTaskService formatTaskService;

    private final TaskNotificationService userNotificationService;

    public NotificationService(TelegramService telegramService,
                               com.vaka.daily.service.scheduler.TaskNotificationService notificationService, FormatTaskService formatTaskService,
                               TaskNotificationService userNotificationService) {
        this.telegramService = telegramService;
        this.notificationService = notificationService;
        this.formatTaskService = formatTaskService;
        this.userNotificationService = userNotificationService;
    }

    @Transactional
    public void notifyUsers() {
        List<Task> tasksForNotification = notificationService.getTasksForNotification();
        Map<User, List<Task>> userTasksMap = tasksForNotification.stream()
                .collect(Collectors.groupingBy((task -> task.getSchedule().getUser())));

        for (var entry : userTasksMap.entrySet()) {
            User user = entry.getKey();
            List<Task> tasks = entry.getValue();

            notifyUserByTelegram(user, tasks);
        }
    }

    private boolean shouldNotify(User user, Task task) {
        TaskNotification userNotification;

        try {
            userNotification = userNotificationService.getByTaskId(task.getId());
        } catch (UserNotFoundException ex) {
            return true;
        }

        LocalDateTime lastNotified = userNotification.getLastNotifiedAt();

        if (isTaskSingular(task)) {
            int daysFromLastNotified = getDaysFrom(lastNotified);

            return daysFromLastNotified >= 1;
        }

        if (isTaskRegular(task)) {
            int daysFromLastNotified = getDaysFrom(lastNotified);

            if (daysFromLastNotified < 1) {
                return false;
            }

            int daysToDeadLine = getDaysTo(task.getDeadline());
            long minutesToDeadLine = getMinutesTo(task.getDeadline());

            return daysToDeadLine == 1
                    || minutesToDeadLine == 5
                    || minutesToDeadLine == 0;
        }

        if (isTaskRepetitive(task)) {
            if (isTaskDeadLineLater(task)) {
                int daysFromLastNotified = getDaysFrom(lastNotified);

                if (daysFromLastNotified < 1) {
                    return false;
                }

                int daysToDeadLine = getDaysTo(task.getDeadline());
                return daysToDeadLine == 1;
            } else {
                int daysFromLastNotified = getDaysFrom(lastNotified);

                return daysFromLastNotified >= 1;
            }
        }

        return true;
    }

    private void notifyUserByTelegram(User user, List<Task> tasks) {
        if (!telegramEnabled) {
            log.info("Telegram is disabled");
            return;
        }

        if (user.getTelegramId() == null) {
            return;
        }

        boolean messageSent = false;

        for (Task task : tasks) {
            if (shouldNotify(user, task)) {
                String msg = formatTaskService.formatTaskForNotification(task);
                messageSent = telegramService.sendMessage(user.getTelegramId(), msg);
            }
        }

        if (!messageSent) {
            return;
        }

        try {
            TaskNotification userNotification = userNotificationService.getByUserId(user.getId());
            userNotification.setLastNotifiedAt(LocalDateTime.now());

            userNotificationService.updateById(userNotification.getId(), userNotification);
        } catch (UserNotFoundException ex) {
            TaskNotification userNotification = new TaskNotification();
            userNotification.setUser(user);
            userNotification.setLastNotifiedAt(LocalDateTime.now());

            userNotificationService.create(userNotification);
        }
    }
}
