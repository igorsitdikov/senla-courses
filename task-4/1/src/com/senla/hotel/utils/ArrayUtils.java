package com.senla.hotel.utils;

import java.lang.reflect.Array;

public class ArrayUtils {
    public <T> T[] expandArray(final Class<T> tClass, final T[] array) {
        @SuppressWarnings("unchecked") final T[] buffer = (T[]) Array.newInstance(tClass, array.length + 1);
        System.arraycopy(array, 0, buffer, 0, array.length);
        return buffer;
    }
}
