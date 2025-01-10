package com.vaka.daily.controller;

import com.vaka.daily.domain.Schedule;
import com.vaka.daily.domain.dto.ScheduleDto;
import com.vaka.daily.domain.mapper.ScheduleMapper;
import com.vaka.daily.exception.ValidationException;
import com.vaka.daily.service.domain.ScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@Slf4j
public class ScheduleController {
    private final ScheduleService service;
    private final ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService service, ScheduleMapper scheduleMapper) {
        this.service = service;
        this.scheduleMapper = scheduleMapper;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "user_id", required = false) Integer userId) {
        if (name != null && userId != null) {
            // TODO: 6/19/2024 Implements criteria and specifications
        } else if (name != null) {
            return ResponseEntity.ok(service.getByName(name));
        }

        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ScheduleDto scheduleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Schedule schedule = scheduleMapper.fromDto(scheduleDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody @Valid ScheduleDto scheduleDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Schedule updated = service.updateById(id, scheduleMapper.fromDto(scheduleDto));

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
