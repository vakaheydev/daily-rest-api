package com.vaka.daily.exception;

public class TaskNotificationNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "Task Notification";

    public TaskNotificationNotFoundException(Integer id, String name) {
        super(OBJECT_NAME, id, name);
    }

    public TaskNotificationNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }

    public TaskNotificationNotFoundException(String name) {
        super(OBJECT_NAME, name);
    }
}
