package com.senla.hotel.mapper.dto;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.DTOIsEmptyException;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.IDataTransferObjectMapper;

public class ResidentDTOMapper implements IDataTransferObjectMapper<Resident, ResidentDTO> {
    private static final RoomHistoryDTOMapper historyMapper = new RoomHistoryDTOMapper();

    @Override
    public Resident sourceToDestination(final ResidentDTO source) throws DTOIsEmptyException {
        if (source == null) {
            throw new DTOIsEmptyException("ResidentDTO is empty");
        }

        final Resident resident = new Resident();
        resident.setId(source.getId());
        resident.setFirstName(source.getFirstName());
        resident.setLastName(source.getLastName());
        resident.setGender(source.getGender());
        resident.setPhone(source.getPhone());
        resident.setVip(source.getVip());
        resident.setHistory(historyMapper.sourceToDestination(source.getHistory()));

        return resident;
    }

    @Override
    public ResidentDTO destinationToSource(final Resident destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Resident is null");
        }

        final ResidentDTO residentDTO = new ResidentDTO();
        residentDTO.setId(destination.getId());
        residentDTO.setFirstName(destination.getFirstName());
        residentDTO.setLastName(destination.getLastName());
        residentDTO.setGender(destination.getGender());
        residentDTO.setPhone(destination.getPhone());
        residentDTO.setVip(destination.getVip());
        residentDTO.setHistory(historyMapper.destinationToSource(destination.getHistory()));

        return residentDTO;
    }
}
