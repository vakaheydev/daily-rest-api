package com.vaka.daily.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@Order(1)
public class MethodArgumentNotValidExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentTypeMismatchExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("{} | Validation error: {}", ex.getClass().getSimpleName(), ex.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message", String.format("Validation error: %s", ex.getMessage()));
        map.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return map;
    }
}
