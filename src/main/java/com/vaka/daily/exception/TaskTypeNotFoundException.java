package com.vaka.daily.exception;

public class TaskTypeNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "TaskType";

    public TaskTypeNotFoundException(Integer id, String name) {
        super(OBJECT_NAME, id, name);
    }

    public TaskTypeNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }

    public TaskTypeNotFoundException(String name) {
        super(OBJECT_NAME, name);
    }
}
