package com.senla.hotel.mapper;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.csvMapper.AttendanceMapper;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;

import java.math.BigDecimal;

public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public Attendance sourceToDestination(final String source) throws EntityIsEmptyException {
        if (source.isEmpty()) {
            throw new EntityIsEmptyException("Attendance is empty");
        }
        final String[] elements = source.split(SEPARATOR);

        final Attendance attendance = new Attendance();
        attendance.setId(Long.valueOf(elements[0]));
        attendance.setPrice(new BigDecimal(elements[1]));
        attendance.setName(elements[2]);

        return attendance;
    }

    @Override
    public String destinationToSource(final Attendance destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Attendance is null");
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(destination.getId());
        sb.append(SEPARATOR);
        sb.append(destination.getPrice());
        sb.append(SEPARATOR);
        sb.append(destination.getName());

        return sb.toString();
    }
}
