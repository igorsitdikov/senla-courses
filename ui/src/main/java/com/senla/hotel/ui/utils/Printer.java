package com.senla.hotel.ui.utils;

import com.senla.hotel.ui.exceptions.ListEntitiesIsEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.IntStream;

public class Printer {

    private static final Logger logger = LogManager.getLogger(Printer.class);

    public static <T> void show(final List<T> entities) {
        entities.forEach(logger::info);
    }

    public static <T> void show(final T entity) {
        logger.info(entity);
    }

    public static <T> void show(final List<T> entities, final String text) throws ListEntitiesIsEmptyException {
        if (entities.isEmpty()) {
            throw new ListEntitiesIsEmptyException(String.format("No %ss for choosing", text));
        }
        logger.info("Choose {}:", text);
        IntStream.range(0, entities.size())
            .forEach(index -> logger.info("{}. {}", (index + 1), entities.get(index)));
    }
}
