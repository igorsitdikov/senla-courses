package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomHistoryDtoMapperImpl implements RoomHistoryDtoMapper {

    @Autowired
    private ResidentDtoMapper residentDtoMapper;
    @Autowired
    private RoomDtoMapper roomDtoMapper;
    @Autowired
    private AttendanceDtoMapper attendanceDtoMapper;

    @Override
    public RoomHistory sourceToDestination(final RoomHistoryDTO source) throws EntityIsEmptyException {
        if (source == null) {
            throw new EntityIsEmptyException("RoomHistory is null");
        }

        final RoomHistory destination = new RoomHistory();
        destination.setId(source.getId());
        destination.setCheckIn(Date.valueOf(source.getCheckIn()));
        destination.setCheckOut(Date.valueOf(source.getCheckOut()));
        destination.setResident(residentDtoMapper.sourceToDestination(source.getResident()));
        destination.setRoom(roomDtoMapper.sourceToDestination(source.getRoom()));
        List<Attendance> list = new ArrayList<>();
        for (AttendanceDTO attendanceDTO : source.getAttendances()) {
            Attendance attendance = attendanceDtoMapper.sourceToDestination(attendanceDTO);
            list.add(attendance);
        }
        destination.setAttendances(list);

        return destination;
    }

    @Override
    public RoomHistoryDTO destinationToSource(final RoomHistory destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("RoomHistory is empty");
        }

        final RoomHistoryDTO source = new RoomHistoryDTO();
        source.setId(destination.getId());
        source.setCheckIn(destination.getCheckIn().toLocalDate());
        source.setCheckOut(destination.getCheckOut().toLocalDate());
        source.setResident(residentDtoMapper.destinationToSource(destination.getResident()));
        source.setRoom(roomDtoMapper.destinationToSource(destination.getRoom()));
        List<AttendanceDTO> list = new ArrayList<>();
        for (Attendance attendance : destination.getAttendances()) {
            AttendanceDTO attendanceDTO = attendanceDtoMapper.destinationToSource(attendance);
            list.add(attendanceDTO);
        }
        source.setAttendances(list);

        return source;
    }

}
