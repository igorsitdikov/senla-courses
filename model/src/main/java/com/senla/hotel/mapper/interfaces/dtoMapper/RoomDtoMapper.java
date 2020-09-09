package com.senla.hotel.mapper.interfaces.dtoMapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface RoomDtoMapper {

    Room sourceToDestination(RoomDTO source) throws EntityIsEmptyException;

    RoomDTO destinationToSource(Room destination) throws EntityIsEmptyException;
}
