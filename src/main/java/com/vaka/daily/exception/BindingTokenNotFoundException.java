package com.vaka.daily.exception;

public class BindingTokenNotFoundException extends ObjectNotFoundException {
    public static final String OBJECT_NAME = "Binding Token";

    public static BindingTokenNotFoundException byUserId(Integer userId) {
        var ex = new BindingTokenNotFoundException(
                String.format("Binding Token with user ID {%d} not found", userId));
        ex.details.put("user", String.valueOf(userId));
        return ex;
    }

    public static BindingTokenNotFoundException byId(Integer id) {
        return new BindingTokenNotFoundException(id);
    }

    public static BindingTokenNotFoundException byValue(String value) {
        var ex = new BindingTokenNotFoundException(
                String.format("Binding Token with unique value {%s} not found", value));
        ex.details.put("value", String.valueOf(value));
        return ex;
    }

    private BindingTokenNotFoundException(String message) {
        super(message);
    }

    private BindingTokenNotFoundException(Integer id) {
        super(OBJECT_NAME, id);
    }
}
