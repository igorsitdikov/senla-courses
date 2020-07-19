package com.senla.hotel.utils;

import com.senla.hotel.exception.ConverterNotExistsException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ObjectConverter {
    private static Map<String, Method> converters = new HashMap<>();

    static {
        Method[] methods = ObjectConverter.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length == 1) {
                converters.put(method.getParameterTypes()[0].getName() + "_"
                               + method.getReturnType().getName(), method);
            }
        }
    }

    private ObjectConverter() {
    }

    public static <T> T convert(Object from, Class<T> to) throws ConverterNotExistsException {
        if (from == null) {
            return null;
        }
        if (to.isAssignableFrom(from.getClass())) {
            return to.cast(from);
        }
        String converterId = from.getClass().getName() + "_" + to.getName();
        Method converter = converters.get(converterId);
        if (converter == null) {
            throw new ConverterNotExistsException("Cannot convert from "
                                                  + from.getClass().getName() + " to " + to.getName()
                                                  + ". Requested converter does not exist.");
        }
        try {
            return to.cast(converter.invoke(to, from));
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert from "
                                       + from.getClass().getName() + " to " + to.getName()
                                       + ". Conversion failed with " + e.getMessage(), e);
        }
    }

    public static Double stringToDouble(String value) {
        return Double.valueOf(value);
    }

    public static Integer stringToInteger(String value) {
        return Integer.valueOf(value);
    }

    public static Boolean stringToBoolean(String value) {
        return Boolean.valueOf(value);
    }
}
