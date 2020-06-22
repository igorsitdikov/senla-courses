package com.senla.hotel.utils;

import com.senla.hotel.ui.exceptions.ListEntitiesIsEmptyException;

import java.util.List;
import java.util.stream.IntStream;

public class Printer {
    public static <T> void show(List<T> entities) {
        entities.forEach(System.out::println);
    }

    public static <T> void show(T entity) {
        System.out.println(entity);
    }

    public static <T> void show(List<T> entities, String text) throws ListEntitiesIsEmptyException {
        if (entities.isEmpty()) {
            throw new ListEntitiesIsEmptyException(String.format("No %ss for choosing", text));
        }
        System.out.printf("Choose %s:%n", text);
        IntStream.range(0, entities.size())
            .forEach(index -> String.format("%d. %s", (index + 1), entities.get(index)));
    }
}
