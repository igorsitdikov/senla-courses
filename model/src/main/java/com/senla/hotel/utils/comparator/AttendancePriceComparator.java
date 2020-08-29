package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Attendance;

import java.util.Comparator;

public class AttendancePriceComparator implements Comparator<Attendance> {

    @Override
    public int compare(final Attendance attendanceFirst, final Attendance attendanceSecond) {
        return attendanceFirst.getPrice().compareTo(attendanceSecond.getPrice());
    }
}
