package com.vaka.daily.scheduling;

import com.vaka.daily.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NotificationScheduler {
    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void reportCurrentTime() {
        notificationService.notifyUsers();
    }
}
