package com.vaka.daily.service.scheduler;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.TaskNotification;
import com.vaka.daily.exception.notfound.TaskNotFoundException;
import com.vaka.daily.service.domain.TaskNotificationService;
import com.vaka.daily.service.scheduler.format.FormatTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final FormatTaskService formatTaskService;

    private final TaskNotificationService taskNotificationService;

    @Autowired
    public NotificationService(TelegramService telegramService,
                               FormatTaskService formatTaskService, TaskNotificationService userNotificationService) {
        this.telegramService = telegramService;
        this.formatTaskService = formatTaskService;
        this.taskNotificationService = userNotificationService;
    }

    @Transactional
    public void notifyUsers() {
        List<Task> tasksForNotification = taskNotificationService.getTasksForNotification();
        Map<User, List<Task>> userTasksMap = tasksForNotification.stream()
                .collect(Collectors.groupingBy((task -> task.getSchedule().getUser())));

        for (var entry : userTasksMap.entrySet()) {
            User user = entry.getKey();
            List<Task> tasks = entry.getValue();

            notifyUserByTelegram(user, tasks);
        }
    }

    private void postTaskNotification(Task task) {
        try {
            TaskNotification taskNotification = taskNotificationService.getByTaskId(task.getId());
            taskNotification.setLastNotifiedAt(LocalDateTime.now());

            taskNotificationService.updateById(taskNotification.getId(), taskNotification);
        } catch (TaskNotFoundException ex) {
            TaskNotification taskNotification = new TaskNotification();
            taskNotification.setTask(task);
            taskNotification.setLastNotifiedAt(LocalDateTime.now());

            taskNotificationService.create(taskNotification);
        }
    }

    private boolean shouldNotify(User user, Task task) {
        TaskNotification taskNotification;

        try {
            taskNotification = taskNotificationService.getByTaskId(task.getId());
        } catch (TaskNotFoundException ex) {
            return true;
        }

        LocalDateTime lastNotified = taskNotification.getLastNotifiedAt();

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
        if (user.getTelegramId() == null) {
            return;
        }

        for (Task task : tasks) {
            if (shouldNotify(user, task)) {
                String msg = formatTaskService.formatTaskForNotification(task);
                boolean messageSent = telegramService.sendMessage(user.getTelegramId(), msg);

                if (!messageSent) {
                    continue;
                }

                postTaskNotification(task);
            }
        }
    }
}
