package com.vaka.daily.scheduling;

import com.vaka.daily.service.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class NotificationScheduler implements SchedulingConfigurer {
    @Value("${notification.loop}")
    private Integer secondsLoop;

    public NotificationScheduler(SchedulerService service) {
        this.service = service;
    }

    private final SchedulerService service;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(1));
        taskRegistrar.addFixedRateTask(service::process, Duration.ofSeconds(secondsLoop));
    }
}
