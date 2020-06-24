package com.senla.hotel.mapper.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.NoSuchEntityException;

public interface IEntityMapper<T extends AEntity> {
    String SEPARATOR = ",";

    T sourceToDestination(String source);

    String destinationToSource(T destination);
}
