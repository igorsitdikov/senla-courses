package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.utils.ArrayUtils;

public abstract class ARepository {
    protected ArrayUtils arrayUtils = new ArrayUtils();

    public abstract AEntity add(AEntity entity);
}
