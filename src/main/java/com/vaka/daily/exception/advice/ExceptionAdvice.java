package com.vaka.daily.exception.advice;

import com.vaka.daily.domain.TaskType;
import com.vaka.daily.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    private static final Map<String, Integer> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put("NoResourceFoundException", 404);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(Exception ex) {
        log.error("{} | Exception: {}", ex.getClass().getSimpleName(), ex.getMessage());

        int status = STATUS_MAP.getOrDefault(ex.getClass().getSimpleName(), 400);
        String msg = resolveMessage(ex);

        Map<String, String> body = Map.of(
                "error", ex.getClass().getSimpleName(),
                "message", msg,
                "status", Integer.toString(status)
        );

        return ResponseEntity.status(status).body(body);
    }

    public String resolveMessage(Exception ex) {
        String msg = ex.getMessage();

        if (ex instanceof ValidationException) {
            msg = ((ValidationException) ex).getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).collect(Collectors.joining("; "));
        }

        if (ex instanceof DataIntegrityViolationException ||
            ex instanceof InvalidDataAccessResourceUsageException) {
            return "Database error";
        }

        return "Bad request";
    }
}
