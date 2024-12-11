package com.vaka.daily.service;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.dto.TaskDto;
import com.vaka.daily.exception.TaskNotFoundException;
import com.vaka.daily.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public SimpleTaskService(TaskRepository taskRepository, ScheduleService scheduleService) {
        this.taskRepository = taskRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public Task fromDto(TaskDto dto) {
        return Task.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .status(dto.getStatus())
                .schedule(scheduleService.getById(dto.getScheduleId()))
                .build();
    }

    @Override
    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .scheduleId(task.getSchedule().getId())
                .build();
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Integer id) {
        var temp = taskRepository.findById(id);
        return temp.orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task create(Task entity) {
        return taskRepository.save(entity);
    }

    @Override
    public Task updateById(Integer id, Task entity) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        entity.setId(id);
        return taskRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
    }
}
