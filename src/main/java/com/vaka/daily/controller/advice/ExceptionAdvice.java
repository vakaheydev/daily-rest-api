package com.vaka.daily.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> exceptionHandler(Exception ex) {
        log.error("{} | Unresolved exception: {}", ex.getClass().getSimpleName(), ex.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message",
                String.format("Internal server error: %s", ex.getMessage()));
        map.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return map;
    }
}
