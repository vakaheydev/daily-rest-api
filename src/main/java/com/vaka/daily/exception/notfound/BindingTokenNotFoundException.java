package com.vaka.daily.exception.notfound;

public class BindingTokenNotFoundException extends ObjectNotFoundException {
    public static ObjectNotFoundException byValue(String value) {
        return by("value", value);
    }

    public static ObjectNotFoundException byUserId(Integer userId) {
        return by("userId", userId);
    }
}
