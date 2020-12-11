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
        LocalDate yesterday = LocalDate.now().minusDays(1);
        final LocalDate localDate = dateTimeToDate(localDateTime);
        return localDate.equals(yesterday) || localDate.isBefore(yesterday);
    }

    public static boolean isExpired(LocalDateTime localDateTime, Integer days) {
        LocalDateTime addedDays = addDays(localDateTime, days);
        return isExpired(addedDays);
    }
}
