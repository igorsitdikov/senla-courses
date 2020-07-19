package com.senla.hotel.controller;

import com.senla.annotaion.Autowired;
import com.senla.annotaion.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.interfaces.IAttendanceService;

import java.math.BigDecimal;
import java.util.List;

@Singleton
public class AttendanceController {
    @Autowired
    private static IAttendanceService attendanceService;

    public void createAttendance(final Attendance attendance) throws EntityAlreadyExistsException {
        attendanceService.createAttendance(attendance);
    }

    public void deleteAttendance(final Long id) throws EntityNotFoundException {
        attendanceService.delete(id);
    }

    public List<Attendance> showAttendances() {
        return attendanceService.showAttendances();
    }

    public List<Attendance> showAttendancesSortedByName() {
        return attendanceService.showAttendancesSortedByName();
    }

    public List<Attendance> showAttendancesSortedByPrice() {
        return attendanceService.showAttendancesSortedByPrice();
    }

    public void changePrice(final String name, final BigDecimal price) {
        attendanceService.changeAttendancePrice(name, price);
    }

    public void importAttendances() {
        attendanceService.importAttendances();
    }

    public void exportAttendances() {
        attendanceService.exportAttendances();
    }
}
