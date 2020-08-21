package com.senla.hotel.mapper;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.mapper.interfaces.resultSetMapper.AttendanceResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceResultSetMapperImpl implements AttendanceResultSetMapper {
    @Override
    public Attendance sourceToDestination(final ResultSet source) throws SQLException {
        final Attendance attendance = new Attendance();
        attendance.setId(source.getLong("attendance.id"));
        attendance.setName(source.getString("attendance.name"));
        attendance.setPrice(source.getBigDecimal("attendance.price"));
        return attendance;
    }
}
