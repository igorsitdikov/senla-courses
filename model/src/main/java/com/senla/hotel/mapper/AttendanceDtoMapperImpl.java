package com.senla.hotel.mapper;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AttendanceDtoMapperImpl implements AttendanceDtoMapper {

    @Override
    public Attendance sourceToDestination(final AttendanceDto source) {
        Attendance destination = new Attendance();
        destination.setId(source.getId());
        destination.setPrice(source.getPrice());
        destination.setName(source.getName());
        return destination;
    }

    @Override
    public AttendanceDto destinationToSource(final Attendance destination) {
        AttendanceDto source = new AttendanceDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setName(destination.getName());
        return source;
    }
}
