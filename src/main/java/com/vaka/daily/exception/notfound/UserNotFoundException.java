package com.vaka.daily.exception.notfound;

public class UserNotFoundException extends CommonObjectNotFoundException {
    public static ObjectNotFoundException byTelegramId(Long telegramId) {
        return by("telegramId", telegramId);
    }
}
