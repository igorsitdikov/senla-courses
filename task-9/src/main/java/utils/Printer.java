package utils;

import java.time.LocalDateTime;

public class Printer {
    public static void printName(final Thread t) {
        System.out.printf("Thread name : %s%n", t.getName());
    }

    public static void printException(final Thread t, final Exception e) {
        System.err.printf("%s - %s %n", t.getName(), e.getMessage());
    }

    public static void printState(final String msg, final Thread t) {
        System.out.printf("%s - %s state: %s%n", msg, t.getName(), t.getState());
    }

    public static void printDateTime() {
        System.out.println(LocalDateTime.now());
    }
}
