package com.vaka.daily.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@Order(1)
public class DataIntegrityViolationExceptionAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> methodArgumentTypeMismatchExceptionHandler(DataIntegrityViolationException ex) {
        log.error("{} | Data integrity exception: {}", ex.getClass().getSimpleName(),
                ex.getMostSpecificCause().getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message",
                String.format("Bad request (data integrity exception): %s", ex.getMostSpecificCause().getMessage()));
        map.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        return map;
    }
}
