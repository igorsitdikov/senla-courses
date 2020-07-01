package com.senla.hotel.mapper.dto;

import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.DTOIsEmptyException;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.interfaces.IDataTransferObjectMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.service.RoomHistoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoomDTOMapper implements IDataTransferObjectMapper<Room, RoomDTO> {
    private static final RoomHistoryDTOMapper historyMapper = new RoomHistoryDTOMapper();

    @Override
    public Room sourceToDestination(final RoomDTO source) throws DTOIsEmptyException {
        if (source == null) {
            throw new DTOIsEmptyException("RoomDTO is null");
        }
        final Room room = new Room();
        room.setId(source.getId());
        room.setStatus(source.getStatus());
        room.setStars(source.getStars());
        room.setNumber(source.getNumber());
        room.setAccommodation(source.getAccommodation());
        room.setHistories(source.getHistories()
                              .stream()
                              .map(roomHistory -> {
                                  try {
                                      return historyMapper.sourceToDestination(roomHistory);
                                  } catch (final DTOIsEmptyException e) {
                                      e.printStackTrace();
                                  }
                                  return null;
                              })
                              .filter(Objects::nonNull)
                              .collect(Collectors.toList()));
        room.setPrice(source.getPrice());

        return room;
    }

    @Override
    public RoomDTO destinationToSource(final Room destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Room is null");
        }
        final RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(destination.getId());
        roomDTO.setStatus(destination.getStatus());
        roomDTO.setStars(destination.getStars());
        roomDTO.setNumber(destination.getNumber());
        roomDTO.setAccommodation(destination.getAccommodation());
        roomDTO.setHistories(destination.getHistories()
                                 .stream()
                                 .map(roomHistory -> {
                                     try {
                                         return historyMapper.destinationToSource(roomHistory);
                                     } catch (final EntityIsEmptyException e) {
                                         e.printStackTrace();
                                     }
                                     return null;
                                 })
                                 .filter(Objects::nonNull)
                                 .collect(Collectors.toList()));
        roomDTO.setPrice(destination.getPrice());

        return roomDTO;
    }
}
