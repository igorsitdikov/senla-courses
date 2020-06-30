package com.senla.hotel.ui.utils;

import java.time.LocalDate;
import java.util.Scanner;

public class InputDataReader {
    public static Integer getIntegerInput(Scanner scanner, String message, Integer limit) {
        System.out.println(message);

        boolean isValid = false;
        Integer input = null;
        while (!isValid) {
            input = getIntegerInput(scanner);
            if (input > 0 && input <= limit) {
                isValid = true;
            } else {
                System.out.printf("Number is not valid. Input number 1-%d%n", limit);
            }
        }

        return input;
    }

    public static Integer getIntegerInput(Scanner scanner) {
        boolean isValid = false;
        Integer input = null;

        while (!isValid) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                isValid = true;
            } else {
                System.out.println("Wrong input. Please, input a number.");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static Long getLongInput(Scanner scanner, String message) {
        System.out.println(message);
        return getLongInput(scanner);
    }

    public static Long getLongInput(Scanner scanner) {
        boolean isValid = false;
        Long input = null;

        while (!isValid) {
            if (scanner.hasNextLong()) {
                input = scanner.nextLong();
                isValid = true;
            } else {
                System.out.println("Wrong input. Please, input a number.");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static String getStringInput(Scanner scanner, String message) {
        System.out.println(message);
        return getStringInput(scanner);
    }

    public static String getStringInput(Scanner scanner) {
        return scanner.nextLine();
    }

    public static Double getDoubleInput(Scanner scanner, String message) {
        System.out.println(message);
        return getDoubleInput(scanner);
    }

    public static Double getDoubleInput(Scanner scanner) {
        boolean isValid = false;
        Double input = null;

        while (!isValid) {
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                isValid = true;

            } else {
                System.out.println("Wrong input. Please, input a double.");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static LocalDate getLocalDateInput(Scanner scanner, String message) {
        System.out.println(message);
        return getLocalDateInput(scanner);
    }

    private static LocalDate getLocalDateInput(Scanner scanner) {
        boolean isValid = false;
        String input;
        LocalDate date = null;
        while (!isValid) {
            if (scanner.hasNext()) {
                input = scanner.next();
                date = LocalDate.parse(input);
                isValid = true;
            } else {
                System.out.println("Wrong input. Please, input the date in \"YYYY-MM-DD\" format.");
                scanner.nextLine();
            }
        }
        return date;
    }
}
