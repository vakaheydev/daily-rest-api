package com.vaka.daily.controller;

import com.vaka.daily.domain.Task;
import com.vaka.daily.exception.ValidationException;
import com.vaka.daily.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@Slf4j
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
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
    public ResponseEntity<?> create(@RequestBody @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody @Valid Task task,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return ResponseEntity.ok(service.updateById(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
