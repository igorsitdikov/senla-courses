package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;

import java.math.BigDecimal;
import java.util.Comparator;

public interface IAttendanceService {

    Attendance[] sortAttendances(Attendance[] attendances, Comparator<Attendance> comparator);

    void createAttendance(Attendance attendance);

    Attendance[] showAttendances();

    Attendance[] showAttendancesSortedByName();

    Attendance[] showAttendancesSortedByPrice();

    void changeAttendancePrice(Long id, BigDecimal price);

    void changeAttendancePrice(String name, BigDecimal price);

}
