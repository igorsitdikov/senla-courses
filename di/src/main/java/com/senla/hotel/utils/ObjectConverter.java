package com.senla.hotel.utils;

import com.senla.hotel.exception.ConverterNotExistsException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ObjectConverter {
    private static Map<String, Method> converters = new HashMap<>();

    static {
        final Method[] methods = ObjectConverter.class.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.getParameterTypes().length == 1) {
                converters.put(method.getParameterTypes()[0].getName() + "_"
                               + method.getReturnType().getName(), method);
            }
        }
    }

    private ObjectConverter() {
    }

    public static <T> T convert(final Object from, final Class<T> to) throws ConverterNotExistsException {
        if (from == null) {
            return null;
        }
        if (to.isAssignableFrom(from.getClass())) {
            return to.cast(from);
        }
        final String converterId = from.getClass().getName() + "_" + to.getName();
        final Method converter = converters.get(converterId);
        if (converter == null) {
            throw new ConverterNotExistsException("Cannot convert from "
                                                  + from.getClass().getName() + " to " + to.getName()
                                                  + ". Requested converter does not exist.");
        }
        try {
            return to.cast(converter.invoke(to, from));
        } catch (final Exception e) {
            throw new RuntimeException("Cannot convert from "
                                       + from.getClass().getName() + " to " + to.getName()
                                       + ". Conversion failed with " + e.getMessage(), e);
        }
    }

    public static Double stringToDouble(final String value) {
        return Double.valueOf(value);
    }

    public static Integer stringToInteger(final String value) {
        return Integer.valueOf(value);
    }

    public static Boolean stringToBoolean(final String value) {
        return Boolean.valueOf(value);
    }
}
