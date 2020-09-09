package com.senla.hotel.mapper;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;

public class ResidentDtoMapperImpl implements ResidentDtoMapper {

    @Override
    public Resident sourceToDestination(final ResidentDTO source) throws EntityIsEmptyException {
        if (source == null) {
            throw new EntityIsEmptyException("ResidentDTO is null");
        }
        final Resident destination = new Resident();
        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setGender(source.getGender());
        destination.setVip(source.getVip());
        destination.setPhone(source.getPhone());
        return destination;
    }

    @Override
    public ResidentDTO destinationToSource(final Resident destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Resident is null");
        }
        ResidentDTO source = new ResidentDTO();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setGender(destination.getGender());
        source.setVip(destination.getVip());
        source.setPhone(destination.getPhone());
        return source;
    }
}
