package com.senla.hotel.mapper.interfaces.dtoMapper;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface ResidentDtoMapper {

    Resident sourceToDestination(ResidentDTO source) throws EntityIsEmptyException;

    ResidentDTO destinationToSource(Resident destination) throws EntityIsEmptyException;
}
