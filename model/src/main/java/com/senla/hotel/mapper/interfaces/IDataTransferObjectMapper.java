package com.senla.hotel.mapper.interfaces;

import com.senla.hotel.dto.ADataTransferObject;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.DTOIsEmptyException;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface IDataTransferObjectMapper<T extends AEntity, S extends ADataTransferObject> {
    T sourceToDestination(S source) throws DTOIsEmptyException;

    S destinationToSource(T destination) throws EntityIsEmptyException;
}
