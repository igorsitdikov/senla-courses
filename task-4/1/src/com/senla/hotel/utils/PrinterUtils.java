package com.senla.hotel.utils;

import java.util.List;

public class PrinterUtils {
    public static <T> void show(List<T> entities) {
        entities.forEach(System.out::println);
    }

    public static <T> void show(T entity) {
        System.out.println(entity);
    }
}
