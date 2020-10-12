package com.senla.hotel.mapper;

import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Room;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import org.springframework.stereotype.Component;

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
        ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
        RoomHistoryDtoMapper roomHistoryDtoMapper = new RoomHistoryDtoMapperImpl(residentDtoMapper, this);
        RoomDto source = new RoomDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setStatus(destination.getStatus());
        source.setAccommodation(destination.getAccommodation());
        source.setNumber(destination.getNumber());
        source.setStars(destination.getStars());
        source.setHistoriesDto(destination.getHistories()
                                   .stream()
                                   .map(roomHistoryDtoMapper::destinationToSource)
                                   .collect(Collectors.toList()));
        return source;
    }

}
