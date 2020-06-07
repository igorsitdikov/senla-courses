package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Attendance;

import java.util.Comparator;

public class AttendanceNameComparator implements Comparator<Attendance> {
    @Override
    public int compare(final Attendance attendanceFirst, final Attendance attendanceSecond) {
        return attendanceFirst.getName().compareTo(attendanceSecond.getName());
    }
}
