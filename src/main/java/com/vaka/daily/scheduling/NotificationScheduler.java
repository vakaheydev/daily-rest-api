package com.vaka.daily.scheduling;

import com.vaka.daily.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
public class NotificationScheduler {
    private final NotificationService service;

    @Autowired
    public NotificationScheduler(NotificationService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void deleteExpiredTokens() {
        log.debug("Started notification scheduler");
        service.notifyUsers();
    }
}
