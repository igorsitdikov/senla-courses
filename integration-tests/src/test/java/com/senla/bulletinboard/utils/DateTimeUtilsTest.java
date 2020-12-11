package com.senla.bulletinboard.utils;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeUtilsTest {

    @Test
    public void isExpiredTest() {
        final int twoDays = 2;
        final int oneDay = 1;
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(twoDays);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(oneDay);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(oneDay);
        assertTrue(DateTimeUtils.isExpired(twoDaysAgo));
        assertTrue(DateTimeUtils.isExpired(yesterday));
        assertFalse(DateTimeUtils.isExpired(today));
        assertFalse(DateTimeUtils.isExpired(tomorrow));

        assertTrue(DateTimeUtils.isExpired(twoDaysAgo, oneDay));
        assertFalse(DateTimeUtils.isExpired(yesterday, oneDay));
        assertFalse(DateTimeUtils.isExpired(today, oneDay));
        assertFalse(DateTimeUtils.isExpired(tomorrow, oneDay));


    }
}
