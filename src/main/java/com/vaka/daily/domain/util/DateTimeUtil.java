package com.vaka.daily.domain.util;

import java.time.LocalDateTime;
import java.time.Period;

public class DateTimeUtil {
    public static int getDaysFrom(LocalDateTime date) {
        Period between = Period.between(LocalDateTime.now().toLocalDate(), date.toLocalDate());
        return between.getDays();
    }
}
