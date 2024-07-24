package com.vaka.daily.exception;

public class ScheduleNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "Schedule";
    public ScheduleNotFoundException(Integer id, String name) {
        super(OBJECT_NAME, id, name);
    }

    public ScheduleNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }

    public ScheduleNotFoundException(String name) {
        super(OBJECT_NAME, name);
    }
}
