package com.senla.hotel.mapper;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ResidentDtoMapperImpl implements ResidentDtoMapper {

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
        RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
        RoomHistoryDtoMapper roomHistoryDtoMapper = new RoomHistoryDtoMapperImpl(this, roomDtoMapper);
        ResidentDto source = new ResidentDto();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setGender(destination.getGender());
        source.setPhone(destination.getPhone());
        source.setVip(destination.getVip());
        source.setHistoryDtos(destination.getHistory()
                                  .stream()
                                  .map(roomHistoryDtoMapper::destinationToSource)
                                  .collect(Collectors.toSet()));
        return source;
    }

}
