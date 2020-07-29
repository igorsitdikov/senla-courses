package utils;

import java.time.LocalDateTime;
import java.util.Vector;

public class Printer {
    public static void printName(final Thread t) {
        System.out.printf("Thread name : %s%n", t.getName());
    }

    public static void printException(final Thread t, final Exception e) {
        System.err.printf("%s - %s %n", t.getName(), e.getMessage());
    }

    public static void printState(final Thread t) {
        System.out.printf("%s state: %s%n", t.getName(), t.getState());
    }

    public static void printDateTime() {
        System.out.println(LocalDateTime.now());
    }

    public static void printBufferFull(final Thread t, final Vector buffer) {
        System.out.printf("Buffer is full. %s is waiting. Buffer size: %d%n",
                t.getName(),
                buffer.size());
    }

    public static void printBufferEmpty(final Thread t) {
        System.out.printf("Buffer is empty. %s is waiting.%n", t.getName());
    }

    public static void printConsumedItem(final Integer item) {
        System.out.printf("Consumed item: %d%n", item);
    }

    public static void printProducedItem(final Integer item) {
        System.out.printf("Produce : %d%n", item);
    }
}
