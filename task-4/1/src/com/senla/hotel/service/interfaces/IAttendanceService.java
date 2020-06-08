package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;

import java.util.Comparator;

public interface IAttendanceService {

    void addAttendance(Attendance attendance);

    Attendance[] getAttendances();

    void showAllAttendances();

    Attendance[] sortAttendances(Attendance[] attendances, Comparator<Attendance> comparator);
}
