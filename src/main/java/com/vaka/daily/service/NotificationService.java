package com.vaka.daily.service;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
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

    public NotificationService(TelegramService telegramService, TaskNotificationService notificationService) {
        this.telegramService = telegramService;
        this.notificationService = notificationService;
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
        telegramService.sendMessage(user.getTelegramId(), formatTask(task));
    }

    private String formatTask(Task task) {
        StringBuilder msg = new StringBuilder();
        msg.append(String.format("\nЗадание '%s' | %s | до %s | %s",
                task.getName(),
                task.getDescription(),
                task.getDeadline().toString(),
                task.getStatus() ? "Сделано" : "Надо сделать"));
        msg.append("\n---\n");
        return String.format("Напоминаю <3\nУ Вас есть нерешённая задача: %s", msg);
    }
}
