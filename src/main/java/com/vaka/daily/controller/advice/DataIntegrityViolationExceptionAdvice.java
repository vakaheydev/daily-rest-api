package com.vaka.daily.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
@Order(1)
public class DataIntegrityViolationExceptionAdvice {
    private final Pattern duplicateMsgPattern = Pattern.compile("(\\(.+\\))");

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> methodArgumentTypeMismatchExceptionHandler(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message.contains("duplicate key")) {
            return resolveDuplicateKeyException(ex);
        }

        log.error("{} | Data integrity exception: {}", ex.getClass().getSimpleName(), message);

        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getClass().getSimpleName());
        map.put("message",
                String.format("Bad request (data integrity exception): %s", ex.getMostSpecificCause().getMessage()));
        map.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        return map;
    }

    private Map<String, String> resolveDuplicateKeyException(DataIntegrityViolationException ex) {
        String exMsg = ex.getMostSpecificCause().getMessage();
        log.error("{} | Duplicate: {}", "DuplicateEntityException", exMsg);

        Map<String, String> map = new HashMap<>();
        map.put("error", "DuplicateEntityException");
        map.put("message", getDuplicateEntityExMessage(exMsg));
        map.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        return map;
    }

    private String getDuplicateEntityExMessage(String exMsg) {
        String secondLine = exMsg.split("\n")[1];
        Matcher matcher = duplicateMsgPattern.matcher(secondLine);

        if (matcher.find()) {
            return "Duplicates: " + matcher.group(1);
        }

        throw new IllegalArgumentException("Invalid duplicate key: " + exMsg);
    }
}
