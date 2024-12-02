package com.vaka.daily.exception;

public class UserNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "User";

    public UserNotFoundException(Integer id, String name) {
        super(OBJECT_NAME, id, name);
    }

    public UserNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }

    public UserNotFoundException(Long telegramId) {
        super(String.format("%s with telegram ID {%d} not found", OBJECT_NAME, telegramId));
        putDetail("tgId", String.valueOf(telegramId));
    }

    public UserNotFoundException(String name) {
        super(OBJECT_NAME, name);
    }
}
