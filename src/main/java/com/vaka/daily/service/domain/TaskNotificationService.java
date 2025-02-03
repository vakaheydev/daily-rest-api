package com.vaka.daily.service.domain;

import com.vaka.daily.domain.TaskNotification;
import com.vaka.daily.service.abstraction.CommonService;

public interface TaskNotificationService extends CommonService<TaskNotification> {
    TaskNotification getByTaskId(Integer taskId);
}
