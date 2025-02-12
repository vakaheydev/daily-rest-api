package com.vaka.daily.controller.advice;

import com.vaka.daily.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
@Order(1)
public class ValidationExceptionAdvice {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationExceptionHandler(ValidationException ex) {
        log.error("{} | {}", ex.getClass().getSimpleName(), ex.getBindingResult().getFieldError());

        HashMap<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message", ex.getBindingResult().getFieldError().toString());
        map.put("status", "400");

        return ResponseEntity.badRequest().body(map);
    }
}
