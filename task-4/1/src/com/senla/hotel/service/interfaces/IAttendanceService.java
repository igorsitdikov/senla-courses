package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;

import java.util.Comparator;

public interface IAttendanceService {

    void addAttendance(final Attendance attendance);

    Attendance[] getAttendances();

    void showAllAttendances();

    Attendance[] sortAttendances(final Attendance[] attendances, final Comparator<Attendance> comparator);

}
