package com.senla.hotel.controller;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(final AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void createAttendance(final Attendance attendance) throws EntityAlreadyExistsException, PersistException {
        attendanceService.createAttendance(attendance);
    }

    public void deleteAttendance(final Long id) throws EntityNotFoundException, PersistException {
        attendanceService.delete(id);
    }

    public List<Attendance> showAttendances(final SortField sortField) throws PersistException {
        return attendanceService.showAttendances(sortField);
    }

    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException, PersistException {
        attendanceService.changeAttendancePrice(id, price);
    }

    public void importAttendances() throws PersistException {
        attendanceService.importAttendances();
    }

    public void exportAttendances() throws PersistException {
        attendanceService.exportAttendances();
    }
}
