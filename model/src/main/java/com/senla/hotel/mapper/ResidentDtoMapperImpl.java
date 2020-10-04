package com.senla.hotel.mapper;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ResidentDtoMapperImpl implements ResidentDtoMapper {

    @Autowired
    private RoomDtoMapper roomDtoMapper;

    @Override
    public Resident sourceToDestination(final ResidentDto source) {
        Resident destination = new Resident();
        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setGender(source.getGender());
        destination.setPhone(source.getPhone());
        destination.setVip(source.getVip());
        return destination;
    }

    @Override
    public ResidentDto destinationToSource(final Resident destination) {
        ResidentDto source = new ResidentDto();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setGender(destination.getGender());
        source.setPhone(destination.getPhone());
        source.setVip(destination.getVip());
//        if (destination.getHistory() != null)
//            source.setHistoryDtos(historyEntitiesToListDto(destination.getHistory()));
        return source;
    }

    private Set<RoomHistoryDto> historyEntitiesToListDto(Set<RoomHistory> histories) {
        Set<RoomHistoryDto> roomHistoryDtos = new HashSet<>();
        for (RoomHistory roomHistory : histories) {
            roomHistoryDtos.add(entityToDto(roomHistory));
        }
        return roomHistoryDtos;
    }

    private RoomHistoryDto entityToDto(RoomHistory roomHistory) {
        RoomHistoryDto roomHistoryDto = new RoomHistoryDto();
        roomHistoryDto.setId(roomHistory.getId());
        roomHistoryDto.setCheckOut(roomHistory.getCheckOut());
        roomHistoryDto.setCheckIn(roomHistory.getCheckIn());
        roomHistoryDto.setRoomDto(roomDtoMapper.destinationToSource(roomHistory.getRoom()));
        roomHistoryDto.setStatus(roomHistory.getStatus());
        return roomHistoryDto;
    }
}
