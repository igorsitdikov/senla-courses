package com.senla.hotel.mapper.interfaces.dtoMapper;

import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface RoomHistoryDtoMapper {

    RoomHistory sourceToDestination(RoomHistoryDTO source) throws EntityIsEmptyException;

    RoomHistoryDTO destinationToSource(RoomHistory destination) throws EntityIsEmptyException;
}
