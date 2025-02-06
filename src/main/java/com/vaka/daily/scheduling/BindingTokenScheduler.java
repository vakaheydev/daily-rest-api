package com.vaka.daily.scheduling;

import com.vaka.daily.service.domain.BindingTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BindingTokenScheduler {
    BindingTokenService bindingTokenService;

    public BindingTokenScheduler(BindingTokenService bindingTokenService) {
        this.bindingTokenService = bindingTokenService;
    }

    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.SECONDS)
    public void deleteExpiredTokens() {
        log.debug("Deleting expired tokens");
        bindingTokenService.deleteExpiredTasks();
    }
}
