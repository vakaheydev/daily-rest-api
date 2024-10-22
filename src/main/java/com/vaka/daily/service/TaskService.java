package com.vaka.daily.service;

import com.vaka.daily.abstraction.CommonService;
import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.dto.TaskDto;

public interface TaskService extends CommonService<Task> {
    Task fromDto(TaskDto dto);

    TaskDto toDto(Task task);
}
