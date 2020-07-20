package com.senla.hotel.mapper.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface EntityMapper<T extends AEntity> {
    String SEPARATOR = ",";

    T sourceToDestination(String source) throws EntityIsEmptyException;

    String destinationToSource(T destination) throws EntityIsEmptyException;
}
