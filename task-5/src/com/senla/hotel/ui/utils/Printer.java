package com.senla.hotel.ui.utils;

import java.util.List;

public class Printer {
    public static <T> void show(List<T> entities) {
        entities.forEach(System.out::println);
    }

    public static <T> void show(T entity) {
        System.out.println(entity);
    }
}
