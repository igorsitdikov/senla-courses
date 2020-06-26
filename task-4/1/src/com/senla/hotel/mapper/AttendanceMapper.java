package com.senla.hotel.mapper;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.mapper.interfaces.IEntityMapper;

import java.math.BigDecimal;

public class AttendanceMapper implements IEntityMapper<Attendance> {

    @Override
    public Attendance sourceToDestination(final String source) {
        if (source == null) {
            throw new NullPointerException();
        }
        final String[] elements = source.split(SEPARATOR);

        final Attendance attendance = new Attendance();
        attendance.setId(Long.valueOf(elements[0]));
        attendance.setPrice(new BigDecimal(elements[1]));
        attendance.setName(elements[2]);

        return attendance;
    }

    @Override
    public String destinationToSource(final Attendance destination) {
        if (destination == null) {
            throw new NullPointerException();
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
