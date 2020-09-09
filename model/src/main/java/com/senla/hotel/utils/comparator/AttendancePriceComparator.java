package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.entity.Attendance;

import java.util.Comparator;

public class AttendancePriceComparator implements Comparator<AttendanceDTO> {

    @Override
    public int compare(final AttendanceDTO attendanceFirst, final AttendanceDTO attendanceSecond) {
        return attendanceFirst.getPrice().compareTo(attendanceSecond.getPrice());
    }
}
