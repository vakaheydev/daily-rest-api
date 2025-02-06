package com.vaka.daily.service.domain;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.TaskNotification;
import com.vaka.daily.exception.TaskNotFoundException;
import com.vaka.daily.exception.TaskNotificationNotFoundException;
import com.vaka.daily.repository.TaskNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {
    private final TaskNotificationRepository repository;

    public TaskNotificationServiceImpl(TaskNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskNotification> getAll() {
        return repository.findAll();
    }

    @Override
    public TaskNotification getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotificationNotFoundException(id));
    }

    @Override
    public TaskNotification getByTaskId(Integer taskId) {
        return repository.findByTaskId(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public TaskNotification create(TaskNotification entity) {
        return repository.save(entity);
    }

    @Override
    public TaskNotification updateById(Integer id, TaskNotification entity) {
        if (!repository.existsById(id)) {
            throw new TaskNotificationNotFoundException(id);
        }

        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new TaskNotificationNotFoundException(id);
        }

        repository.deleteById(id);
    }

    @Override
    public List<Task> getTasksForNotification() {
        return repository.findTasksForNotification(LocalDateTime.now());
    }
}
