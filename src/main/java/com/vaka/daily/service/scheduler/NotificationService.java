package com.vaka.daily.service.scheduler;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.service.scheduler.format.FormatTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class NotificationService {
    @Value("${telegram.enabled}")
    private boolean telegramEnabled;
    private final TelegramService telegramService;
    private final TaskNotificationService notificationService;
    private final FormatTaskService formatTaskService;

    public NotificationService(TelegramService telegramService, TaskNotificationService notificationService,
                               FormatTaskService formatTaskService) {
        this.telegramService = telegramService;
        this.notificationService = notificationService;
        this.formatTaskService = formatTaskService;
    }

    @Transactional
    public void notifyUsers() {
        List<Task> tasksForNotification = notificationService.getTasksForNotification();

        for (Task task : tasksForNotification) {
            User user = task.getSchedule().getUser();

            if (telegramEnabled && user.getTelegramId() != null) {
                notifyUserByTelegram(user, task);
            }
        }
    }

    private void notifyUserByTelegram(User user, Task task) {
        String msg = formatTaskService.formatTaskForNotification(task);

        if (msg != null) {
            telegramService.sendMessage(user.getTelegramId(), msg);
        }
    }
}
