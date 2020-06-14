package com.senla.hotel.utils;

import com.senla.hotel.entity.AEntity;

public class ArrayUtils {
    public AEntity[] expandArray(final AEntity[] array) {
        final AEntity[] buffer = new AEntity[array.length + 1];
        System.arraycopy(array, 0, buffer, 0, array.length);
        return buffer;
    }
}
