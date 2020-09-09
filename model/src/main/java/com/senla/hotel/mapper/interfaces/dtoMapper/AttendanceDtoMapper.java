package com.senla.hotel.mapper.interfaces.dtoMapper;

import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityIsEmptyException;

public interface AttendanceDtoMapper {

    Attendance sourceToDestination(AttendanceDTO source) throws EntityIsEmptyException;

    AttendanceDTO destinationToSource(Attendance destination) throws EntityIsEmptyException;
}
