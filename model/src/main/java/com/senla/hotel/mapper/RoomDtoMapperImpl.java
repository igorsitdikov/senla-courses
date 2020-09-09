package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class RoomDtoMapperImpl implements RoomDtoMapper {
    @Autowired
    private RoomHistoryDtoMapper historyDtoMapper;
    @Override
    public Room sourceToDestination(final RoomDTO source) throws EntityIsEmptyException {
        if (source == null) {
            throw new EntityIsEmptyException("Room string is empty");
        }

        final Room destination = new Room();
        destination.setId(source.getId());
        destination.setNumber(source.getNumber());
        destination.setStars(source.getStars());
        destination.setAccommodation(source.getAccommodation());
        destination.setPrice(source.getPrice());
        destination.setStatus(source.getStatus());
        List<RoomHistory> list = new ArrayList<>();
        for (RoomHistoryDTO roomHistoryDTO : source.getHistories()) {
            RoomHistory roomHistory = historyDtoMapper.sourceToDestination(roomHistoryDTO);
            list.add(roomHistory);
        }
        destination.setHistories(list);

        return destination;
    }

    @Override
    public RoomDTO destinationToSource(final Room destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Room is null");
        }

        final RoomDTO source = new RoomDTO();
        source.setId(destination.getId());
        source.setNumber(destination.getNumber());
        source.setStars(destination.getStars());
        source.setAccommodation(destination.getAccommodation());
        source.setPrice(destination.getPrice());
        source.setStatus(destination.getStatus());
        List<RoomHistoryDTO> list = new ArrayList<>();
        for (RoomHistory roomHistory : destination.getHistories()) {
            RoomHistoryDTO roomHistoryDTO = historyDtoMapper.destinationToSource(roomHistory);
            list.add(roomHistoryDTO);
        }
        source.setHistories(list);

        return source;
    }
}
