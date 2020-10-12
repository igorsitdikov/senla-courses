package com.senla.hotel.mapper.interfaces.dtoMapper;

import com.senla.hotel.dto.ADto;
import com.senla.hotel.entity.AEntity;

public interface DtoMapper<T extends AEntity, E extends ADto> {

    T sourceToDestination(E source);

    E destinationToSource(T destination);
}
