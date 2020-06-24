package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public interface IAttendanceService {

    List<Attendance> sortAttendances(List<Attendance> attendances, Comparator<Attendance> comparator);

    void createAttendance(Attendance attendance);

    List<Attendance> showAttendances();

    List<Attendance> showAttendancesSortedByName();

    List<Attendance> showAttendancesSortedByPrice();

    void changeAttendancePrice(Long id, BigDecimal price);

    void changeAttendancePrice(String name, BigDecimal price);

    void importAttendances();

    void exportAttendances();
}
