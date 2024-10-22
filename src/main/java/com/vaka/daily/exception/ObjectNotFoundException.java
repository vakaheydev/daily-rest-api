package com.vaka.daily.exception;

/**
 * Common exception if object not found by ID, unique name or both
 */
public abstract class ObjectNotFoundException extends RuntimeException {
    private String objectName;
    private Integer id;
    private String name;

    public ObjectNotFoundException(String objectName, Integer id, String name) {
        super(String.format("%s with ID {%d} and unique name (%s) not found", objectName, id, name));
        this.id = id;
        this.name = name;
        this.objectName = objectName;
    }

    public ObjectNotFoundException(String objectName, Integer id) {
        super(String.format("%s with ID {%d} not found", objectName, id));
        this.id = id;
        this.objectName = objectName;
    }

    public ObjectNotFoundException(String objectName, String name) {
        super(String.format("%s with unique name {%s} not found", objectName, name));
        this.name = name;
        this.objectName = objectName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
