package com.vaka.daily.exception.notfound;

public class TaskNotificationNotFoundException extends CommonObjectNotFoundException {
    public static ObjectNotFoundException byTaskId(Integer taskId) {
        return by("taskId", taskId);
    }
}
