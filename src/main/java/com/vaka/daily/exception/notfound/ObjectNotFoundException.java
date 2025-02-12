package com.vaka.daily.exception.notfound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Common exception if object not found by ID, unique name or both
 */
public class ObjectNotFoundException extends RuntimeException {
    private final Map<String, Object> details;

    protected ObjectNotFoundException() {
        details = new HashMap<>();
    }

    protected static ObjectNotFoundException by(String name, Object value) {
        ObjectNotFoundException ex = new ObjectNotFoundException();
        ex.putDetail(name, value);
        return ex;
    }

    public static ObjectNotFoundException byId(Integer id) {
        return by("id", id);
    }

    protected void putDetail(String detailName, Object detailValue) {
        details.put(detailName, detailValue);
    }

    public Optional<Object> getDetail(String detailName) {
        return Optional.ofNullable(details.get(detailName));
    }

    public Set<Map.Entry<String, Object>> getDetails() {
        return Set.copyOf(details.entrySet());
    }
}
