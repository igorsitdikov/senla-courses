package com.senla.hotel.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrinterUtils {

    private static final Logger logger = LogManager.getLogger(PrinterUtils.class);

    public static <T> void show(final List<T> entities) {
        entities.forEach(logger::info);
    }

    public static <T> void show(final T entity) {
        logger.info(entity);
    }
}
