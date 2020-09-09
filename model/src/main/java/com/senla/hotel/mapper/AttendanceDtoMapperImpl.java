package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceDtoMapperImpl implements AttendanceDtoMapper {
    @Autowired
    private RoomHistoryDtoMapper historyDtoMapper;

    @Override
    public Attendance sourceToDestination(final AttendanceDTO source) throws EntityIsEmptyException {
        if (source == null) {
            throw new EntityIsEmptyException("Attendance is empty");
        }

        final Attendance destination = new Attendance();
        destination.setId(source.getId());
        destination.setPrice(source.getPrice());
        destination.setName(source.getName());
        List<RoomHistory> list = new ArrayList<>();
        for (RoomHistoryDTO roomHistoryDTO : source.getHistories()) {
            RoomHistory roomHistory = historyDtoMapper.sourceToDestination(roomHistoryDTO);
            list.add(roomHistory);
        }
        destination.setHistories(list);

        return destination;
    }

    @Override
    public AttendanceDTO destinationToSource(final Attendance destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Attendance is null");
        }
        final AttendanceDTO source = new AttendanceDTO();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setName(destination.getName());
        List<RoomHistoryDTO> list = new ArrayList<>();
        for (RoomHistory roomHistory : destination.getHistories()) {
            RoomHistoryDTO roomHistoryDTO = historyDtoMapper.destinationToSource(roomHistory);
            list.add(roomHistoryDTO);
        }
        source.setHistories(list);

        return source;
    }
}
