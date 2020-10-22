package com.senla.hotel.mapper;

import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomDtoMapperImpl implements RoomDtoMapper {

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
        List<RoomHistoryDto> roomHistoryDtos = new ArrayList<>();
        List<RoomHistory> destinationHistories = destination.getHistories();
        for (RoomHistory destinationHistory : destinationHistories) {
            RoomHistoryDto roomHistoryDto = new RoomHistoryDto();
            roomHistoryDto.setId(destinationHistory.getId());
            roomHistoryDto.setCheckOut(destinationHistory.getCheckOut());
            roomHistoryDto.setCheckIn(destinationHistory.getCheckIn());
            roomHistoryDto.setResidentDto(null);
            roomHistoryDto.setRoomDto(null);
            roomHistoryDto.setStatus(destinationHistory.getStatus());
            roomHistoryDtos.add(roomHistoryDto);
        }
        source.setHistoriesDto(roomHistoryDtos);
        return source;
    }

}
