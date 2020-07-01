package com.senla.hotel.mapper.dto;

import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.DTOIsEmptyException;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.IDataTransferObjectMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;

import java.math.BigDecimal;

public class AttendanceDTOMapper implements IDataTransferObjectMapper<Attendance, AttendanceDTO> {
    @Override
    public Attendance sourceToDestination(final AttendanceDTO source) throws DTOIsEmptyException {
        if (source == null) {
            throw new DTOIsEmptyException("AttendanceDTO is empty");
        }
        final Attendance attendance = new Attendance();
        attendance.setId(source.getId());
        attendance.setName(source.getName());
        attendance.setPrice(source.getPrice());

        return attendance;
    }

    @Override
    public AttendanceDTO destinationToSource(final Attendance destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Attendance is null");
        }
        final AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(destination.getId());
        attendanceDTO.setName(destination.getName());
        attendanceDTO.setPrice(destination.getPrice());

        return attendanceDTO;
    }
}
