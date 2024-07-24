package com.vaka.daily.controller.advice;

import com.vaka.daily.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ObjectNotFoundControllerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        if (ex.getName() == null) {
            log.error("{} | {} with ID {{}} not found", ex.getClass().getSimpleName(), ex.getObjectName(), ex.getId());
        } else if (ex.getId() == null) {
            log.error("{} | {} with unique name {{}} not found", ex.getClass().getSimpleName(), ex.getObjectName(),
                    ex.getName());
        } else {
            log.error("{} | {} with id {{}} and unique name {{}} not found", ex.getClass().getSimpleName(),
                    ex.getObjectName(),
                    ex.getId(),
                    ex.getName());
        }

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message", ex.getMessage());
        map.put("status", "404");
        return map;
    }
}
