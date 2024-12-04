package com.vaka.daily.scheduling;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.service.NotificationService;
import com.vaka.daily.service.TaskNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationScheduler {
    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void reportCurrentTime() {
        log.debug("Notification scheduler started");
        notificationService.notifyUsers();
    }
}
