package com.senla.hotel.repository;

import com.senla.hotel.utils.ArrayUtils;

public abstract class AbstractRepository<T> {
    protected ArrayUtils arrayUtils = new ArrayUtils();

    public abstract T add(T entity);
}
