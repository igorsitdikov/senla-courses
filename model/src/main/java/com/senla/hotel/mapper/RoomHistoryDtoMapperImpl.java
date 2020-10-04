package com.senla.hotel.mapper;

import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomHistoryDtoMapperImpl implements RoomHistoryDtoMapper {

    @Autowired
    private ResidentDtoMapper residentDtoMapper;
    @Autowired
    private RoomDtoMapper roomDtoMapper;

    @Override
    public RoomHistory sourceToDestination(final RoomHistoryDto source) {
        RoomHistory destination = new RoomHistory();
        destination.setId(source.getId());
        destination.setCheckOut(source.getCheckOut());
        destination.setCheckIn(source.getCheckIn());
        destination.setResident(residentDtoMapper.sourceToDestination(source.getResidentDto()));
        destination.setRoom(roomDtoMapper.sourceToDestination(source.getRoomDto()));
        destination.setStatus(source.getStatus());
        return destination;
    }

    @Override
    public RoomHistoryDto destinationToSource(final RoomHistory destination) {
        RoomHistoryDto source = new RoomHistoryDto();
        source.setId(destination.getId());
        source.setCheckOut(destination.getCheckOut());
        source.setCheckIn(destination.getCheckIn());
        source.setResidentDto(residentDtoMapper.destinationToSource(destination.getResident()));
        source.setRoomDto(roomDtoMapper.destinationToSource(destination.getRoom()));
        source.setStatus(destination.getStatus());
        return source;
    }
}
