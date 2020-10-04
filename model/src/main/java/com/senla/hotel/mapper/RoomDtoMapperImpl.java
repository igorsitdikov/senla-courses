package com.senla.hotel.mapper;

import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomDtoMapperImpl implements RoomDtoMapper {

    @Autowired
    private ResidentDtoMapper residentDtoMapper;

    @Override
    public Room sourceToDestination(final RoomDto source) {
        Room destination = new Room();
        destination.setId(source.getId());
        destination.setPrice(source.getPrice());
        destination.setStatus(source.getStatus());
        destination.setAccommodation(source.getAccommodation());
        destination.setNumber(source.getNumber());
        destination.setStars(source.getStars());
        return destination;
    }

    @Override
    public RoomDto destinationToSource(final Room destination) {
        RoomDto source = new RoomDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setStatus(destination.getStatus());
        source.setAccommodation(destination.getAccommodation());
        source.setNumber(destination.getNumber());
        source.setStars(destination.getStars());
        source.setHistoriesDto(historyEntitiesToListDto(destination.getHistories(), source));
        return source;
    }

    private List<RoomHistoryDto> historyEntitiesToListDto(List<RoomHistory> histories, RoomDto roomDto) {
        List<RoomHistoryDto> roomHistoryDtos = new ArrayList<>();
        for (int i = 0; i < histories.size(); i++) {
            roomHistoryDtos.add(entityToDto(histories.get(i), roomDto));
        }
        return roomHistoryDtos;
    }

    private RoomHistoryDto entityToDto(RoomHistory roomHistory, RoomDto roomDto) {
        RoomHistoryDto roomHistoryDto = new RoomHistoryDto();
        roomHistoryDto.setId(roomHistory.getId());
        roomHistoryDto.setCheckOut(roomHistory.getCheckOut());
        roomHistoryDto.setCheckIn(roomHistory.getCheckIn());
        roomHistoryDto.setResidentDto(residentDtoMapper.destinationToSource(roomHistory.getResident()));
        roomHistoryDto.setRoomDto(roomDto);
        roomHistoryDto.setStatus(roomHistory.getStatus());
        return roomHistoryDto;
    }
}
