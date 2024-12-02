package com.vaka.daily.controller.advice;

import com.vaka.daily.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ObjectNotFoundControllerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        Map<String, Object> map = new HashMap<>();

        String missingFields = generateMissingFieldsInfo(ex);
        Map<String, Object> details = generateDetails(ex, missingFields);

        map.put("error", ex.getClass().getSimpleName());
        map.put("message", ex.getMessage());
        map.put("status", "404");
        map.put("details", details);

        return map;
    }

    private String generateMissingFieldsInfo(ObjectNotFoundException ex) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> details = ex.getDetails();

        for (var detail : details) {
            sb.append(detail.getKey()).append(";");
        }

        return sb.toString();
    }

    private Map<String, Object> generateDetails(ObjectNotFoundException ex, String missingFields) {
        Map<String, Object> reqDetails = new HashMap<>();
        Set<Map.Entry<String, String>> exDetails = ex.getDetails();

        reqDetails.put("missingFields", missingFields);

        for (var detail : exDetails) {
            reqDetails.put("requested" + StringUtils.capitalize(detail.getKey()), detail.getValue());
        }

        return reqDetails;
    }
}
