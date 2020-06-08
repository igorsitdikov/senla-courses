package com.senla.hotel.repository;

import com.senla.hotel.entity.AbstractEntity;
import com.senla.hotel.utils.ArrayUtils;

public abstract class AbstractRepository {
    protected ArrayUtils arrayUtils = new ArrayUtils();

    public abstract AbstractEntity add(AbstractEntity entity);
}
