package utils;

public class Printer {
    public static void printName(final Thread t) {
        System.out.printf("Thread name : %s%n", t.getName());
    }

    public static void printException(final Thread t, Exception e) {
        System.err.printf("%s - %s %n", t.getName(), e.getMessage());
    }
}
