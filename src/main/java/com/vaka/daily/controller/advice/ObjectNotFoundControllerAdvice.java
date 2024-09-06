package com.vaka.daily.controller.advice;

import com.vaka.daily.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
@Slf4j
public class ObjectNotFoundControllerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        Map<String, Object> details = new HashMap<>();
        StringBuilder missingFields = new StringBuilder();
        String requestedId = String.valueOf(ex.getId());
        String requestedName = ex.getName();

        if (ex.getName() == null) {
            missingFields.append("id;");
            log.error("{} | {} with ID {{}} not found", ex.getClass().getSimpleName(), ex.getObjectName(), ex.getId());
        } else if (ex.getId() == null) {
            missingFields.append("name;");
            log.error("{} | {} with unique name {{}} not found", ex.getClass().getSimpleName(), ex.getObjectName(),
                    ex.getName());
        } else {
            missingFields.append("name;");
            missingFields.append("id;");
            log.error("{} | {} with id {{}} and unique name {{}} not found", ex.getClass().getSimpleName(),
                    ex.getObjectName(),
                    ex.getId(),
                    ex.getName());
        }

        Map<String, Object> map = new HashMap<>();

        details.put("missingFields", missingFields.toString());
        details.put("requestedId", requestedId);
        details.put("requestedName", requestedName);

        map.put("error", ex.getClass().getSimpleName());
        map.put("message", ex.getMessage());
        map.put("status", "404");
        map.put("details", details);

        return map;
    }
}
