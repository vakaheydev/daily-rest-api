package com.vaka.daily.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Common exception if object not found by ID, unique name or both
 */
public abstract class ObjectNotFoundException extends RuntimeException {
    protected final Map<String, String> details = new HashMap<>();
    @Getter
    protected final String objectName;

    public ObjectNotFoundException(String message) {
        super(message);
        this.objectName = "UNDEFINED";
    }

    public ObjectNotFoundException(String objectName, Integer id, String name) {
        super(String.format("%s with ID {%d} and unique name (%s) not found", objectName, id, name));
        details.put("id", String.valueOf(id));
        details.put("name", name);
        this.objectName = objectName;
    }

    public ObjectNotFoundException(String objectName, Integer id) {
        super(String.format("%s with ID {%d} not found", objectName, id));
        details.put("id", String.valueOf(id));
        this.objectName = objectName;
    }

    public ObjectNotFoundException(String objectName, String name) {
        super(String.format("%s with unique name {%s} not found", objectName, name));
        details.put("name", name);
        this.objectName = objectName;
    }

    public void putDetail(String detailName, String detailValue) {
        details.put(detailName, detailValue);
    }

    public Optional<String> getDetail(String detailName) {
        return Optional.ofNullable(details.get(detailName));
    }

    public Set<Map.Entry<String, String>> getDetails() {
        return Set.copyOf(details.entrySet());
    }

    public Integer getId() {
        return Integer.valueOf(getDetail("id").orElseThrow());
    }

    public String getName() {
        return getDetail("name").orElseThrow();
    }
}
