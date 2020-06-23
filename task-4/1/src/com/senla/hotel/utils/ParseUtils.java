package com.senla.hotel.utils;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.mapper.AttendanceMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseUtils {
    private static IEntityMapper attendanceMapper = new AttendanceMapper();

    public static List<Attendance> stringToAttendances(final Stream<String> stream) {
        return stream
            .skip(1)
            .map(line -> (Attendance) attendanceMapper.sourceToDestination(line))
            .collect(Collectors.toList());
    }

    public static List<String> attendancesToString(final List<Attendance> attendances) {
        return attendances.stream()
            .map(attendance -> attendanceMapper.destinationToSource(attendance))
            .collect(Collectors.toList());
    }
}
