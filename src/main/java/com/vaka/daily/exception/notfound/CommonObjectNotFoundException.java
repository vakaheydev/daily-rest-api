package com.vaka.daily.exception.notfound;

public class CommonObjectNotFoundException extends ObjectNotFoundException {
    public static ObjectNotFoundException byName(String name) {
        return by("name", name);
    }
}
