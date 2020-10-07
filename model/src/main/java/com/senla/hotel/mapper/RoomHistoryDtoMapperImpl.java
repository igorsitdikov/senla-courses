package com.senla.hotel.mapper;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomHistoryDtoMapperImpl implements RoomHistoryDtoMapper {

    @Override
    public RoomHistory sourceToDestination(final RoomHistoryDto source) {
        RoomHistory destination = new RoomHistory();
        destination.setId(source.getId());
        destination.setCheckOut(source.getCheckOut());
        destination.setCheckIn(source.getCheckIn());
        destination.setResident(sourceResidentToDestinationResident(source.getResidentDto()));
        destination.setRoom(sourceRoomToDestinationRoom(source.getRoomDto()));
        destination.setStatus(source.getStatus());
        return destination;
    }

    @Override
    public RoomHistoryDto destinationToSource(final RoomHistory destination) {
        RoomHistoryDto source = new RoomHistoryDto();
        source.setId(destination.getId());
        source.setCheckOut(destination.getCheckOut());
        source.setCheckIn(destination.getCheckIn());
        source.setResidentDto(destinationResidentToSourceResident(destination.getResident()));
        source.setRoomDto(destinationRoomToSourceRoom(destination.getRoom()));
        source.setStatus(destination.getStatus());
        return source;
    }

    private Resident sourceResidentToDestinationResident(final ResidentDto source) {
        Resident destination = new Resident();
        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setGender(source.getGender());
        destination.setPhone(source.getPhone());
        destination.setVip(source.getVip());
        return destination;
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

    private Room sourceRoomToDestinationRoom(final RoomDto source) {
        Room destination = new Room();
        destination.setId(source.getId());
        destination.setPrice(source.getPrice());
        destination.setStatus(source.getStatus());
        destination.setAccommodation(source.getAccommodation());
        destination.setNumber(source.getNumber());
        destination.setStars(source.getStars());
        return destination;
    }

    private RoomDto destinationRoomToSourceRoom(final Room destination) {
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
}
