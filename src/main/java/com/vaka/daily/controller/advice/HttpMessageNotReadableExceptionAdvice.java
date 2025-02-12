package com.vaka.daily.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@Order(1)
public class HttpMessageNotReadableExceptionAdvice {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> httpMessageNotReadableExceptionAdvice(HttpMessageNotReadableException ex) {
        log.error("{} | Incorrent JSON: {}", ex.getClass().getSimpleName(),
                ex.getMostSpecificCause().getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message", "Bad request (incorrect JSON)");
        map.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return map;
    }
}
