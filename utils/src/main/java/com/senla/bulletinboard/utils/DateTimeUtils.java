package com.senla.bulletinboard.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtils {

    public static LocalDate dateTimeToDate(final LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    public static LocalDateTime addDays(final LocalDateTime localDateTime, final Integer days) {
        return localDateTime.plusDays(days).toLocalDate().atStartOfDay();
    }

    public static boolean isExpired(LocalDateTime localDateTime) {
        LocalDate now = LocalDate.now();
        final LocalDate localDate = dateTimeToDate(localDateTime);
        return localDate.equals(now) || localDate.isAfter(now);
    }

    public static boolean isExpired(LocalDateTime localDateTime, Integer days) {
        LocalDate now = LocalDate.now();
        final LocalDate localDate = dateTimeToDate(addDays(localDateTime, days));
        return localDate.equals(now) || localDate.isAfter(now);
    }

}
