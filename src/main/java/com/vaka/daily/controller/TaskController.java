package com.vaka.daily.controller;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.dto.TaskDto;
import com.vaka.daily.exception.ValidationException;
import com.vaka.daily.service.ScheduleService;
import com.vaka.daily.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final ScheduleService scheduleService;

    public TaskController(TaskService taskService, ScheduleService scheduleService) {
        this.taskService = taskService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(taskService.getAll().stream().map(taskService::toDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(taskService.toDto(taskService.getById(id)));
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> search(@RequestParam(name = "name", required = false) String name,
//                                    @RequestParam(name = "user_id", required = false) Integer userId) {
//        if(name != null && userId != null) {
//            // TODO: 6/19/2024 Implements criteria and specifications
//        }
//        else if(name != null) {
//            return ResponseEntity.ok(service.getByName(name));
//        }
//
//        return ResponseEntity.ok(service.getByUserId(userId));
//    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TaskDto taskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Task task = taskService.fromDto(taskDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.toDto(taskService.create(task)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody @Valid TaskDto taskDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Task updated = taskService.updateById(id, taskService.fromDto(taskDto));

        return ResponseEntity.ok(taskService.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
