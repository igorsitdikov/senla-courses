package com.senla.hotel.mapper;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        source.setHistoriesDto(historyEntitiesToListDto(destination.getHistories()));
        return source;
    }

    private List<RoomHistoryDto> historyEntitiesToListDto(List<RoomHistory> histories) {
        List<RoomHistoryDto> roomHistoryDtos = new ArrayList<>();
        for (int i = 0; i < histories.size(); i++) {
            roomHistoryDtos.add(entityToDto(histories.get(i)));
        }
        return roomHistoryDtos;
    }

    private RoomHistoryDto entityToDto(RoomHistory roomHistory) {
        RoomHistoryDto roomHistoryDto = new RoomHistoryDto();
        roomHistoryDto.setId(roomHistory.getId());
        roomHistoryDto.setCheckOut(roomHistory.getCheckOut());
        roomHistoryDto.setCheckIn(roomHistory.getCheckIn());
        roomHistoryDto.setResidentDto(destinationResidentToSourceResident(roomHistory.getResident()));
        roomHistoryDto.setStatus(roomHistory.getStatus());
        return roomHistoryDto;
    }

    private ResidentDto destinationResidentToSourceResident(final Resident destination) {
        ResidentDto source = new ResidentDto();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setGender(destination.getGender());
        source.setPhone(destination.getPhone());
        source.setVip(destination.getVip());
        return source;
    }
}
