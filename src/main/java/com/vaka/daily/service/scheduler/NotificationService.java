package com.vaka.daily.service.scheduler;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.UserNotification;
import com.vaka.daily.domain.util.DateTimeUtil;
import com.vaka.daily.service.domain.UserNotificationService;
import com.vaka.daily.service.scheduler.format.FormatTaskService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.vaka.daily.domain.util.DateTimeUtil.getDaysFrom;
import static com.vaka.daily.domain.util.TaskUtil.*;
import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class NotificationService {
    @Value("${telegram.enabled}")
    private boolean telegramEnabled;
    private final TelegramService telegramService;
    private final TaskNotificationService notificationService;
    private final FormatTaskService formatTaskService;

    private final UserNotificationService userNotificationService;

    public NotificationService(TelegramService telegramService,
                               TaskNotificationService notificationService, FormatTaskService formatTaskService,
                               UserNotificationService userNotificationService) {
        this.telegramService = telegramService;
        this.notificationService = notificationService;
        this.formatTaskService = formatTaskService;
        this.userNotificationService = userNotificationService;
    }

    @Transactional
    public void notifyUsers() {
        List<Task> tasksForNotification = notificationService.getTasksForNotification();

        for (Task task : tasksForNotification) {
            User user = task.getSchedule().getUser();

            if (telegramEnabled && user.getTelegramId() != null && shouldNotify(user, task)) {
                notifyUserByTelegram(user, task);
            }
        }
    }

    private boolean shouldNotify(User user, Task task) {
        if (isTaskSingular(task)) {
            UserNotification userNotification = user.getUserNotification();

            if (userNotification == null) {
                return true;
            }

            LocalDateTime lastNotified = userNotification.getLastNotifiedAt();

            if (lastNotified == null || getDaysFrom(now()) >= 1) {
                return true;
            } else {
                return false;
            }
        }

        if (isTaskRepetitive(task) && isTaskDeadLineLater(task)) {
            int days = getDaysToDeadLine(task);
            return days == 1;
        }

        return true;
    }

    private void notifyUserByTelegram(User user, Task task) {
        String msg = formatTaskService.formatTaskForNotification(task);
        telegramService.sendMessage(user.getTelegramId(), msg);

        UserNotification userNotification = user.getUserNotification();

        if (userNotification == null) {
            userNotification = new UserNotification();
            userNotification.setUser(user);
            userNotification.setLastNotifiedAt(LocalDateTime.now());

            userNotificationService.create(userNotification);
        } else {
            userNotification.setLastNotifiedAt(LocalDateTime.now());

            userNotificationService.updateById(userNotification.getId(), userNotification);
        }
    }
}
