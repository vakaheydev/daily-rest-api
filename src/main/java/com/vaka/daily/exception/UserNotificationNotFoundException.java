package com.vaka.daily.exception;

public class UserNotificationNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "User Notification";

    public UserNotificationNotFoundException(Integer id, String name) {
        super(OBJECT_NAME, id, name);
    }

    public UserNotificationNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }

    public UserNotificationNotFoundException(String name) {
        super(OBJECT_NAME, name);
    }
}
