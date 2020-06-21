package com.senla.hotel.ui.utils;

import java.util.List;
import java.util.stream.IntStream;

public class Printer {
    public static <T> void show(List<T> entities) {
        entities.forEach(System.out::println);
    }

    public static <T> void show(T entity) {
        System.out.println(entity);
    }

    public static <T> void show(List<T> entities, String text) {
        System.out.println(text);
        IntStream.range(0, entities.size())
            .forEach(index -> String.format("%d. %s", (index + 1), entities.get(index)));
    }
}
