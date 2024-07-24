package com.vaka.daily.service;

import com.vaka.daily.domain.Task;
import com.vaka.daily.exception.TaskNotFoundException;
import com.vaka.daily.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public SimpleTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
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
