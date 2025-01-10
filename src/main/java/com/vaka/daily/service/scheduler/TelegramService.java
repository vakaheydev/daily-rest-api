package com.vaka.daily.service.scheduler;

import com.vaka.daily.telegram.TelegramClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@Slf4j
public class TelegramService {
    TelegramClient telegramClient;

    public TelegramService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendMessage(long telegramId, String message) {
        try {
            telegramClient.sendMessage(telegramId, message);
            log.debug("Sent message to tgId={}", telegramId);
        } catch (ResourceAccessException ex) {
            log.error("Telegram bot isn't available");
        }
    }
}
