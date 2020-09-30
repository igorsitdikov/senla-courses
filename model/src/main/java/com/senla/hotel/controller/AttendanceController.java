package com.senla.hotel.controller;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAttendance(final Attendance attendance) throws EntityAlreadyExistsException, PersistException {
        attendanceService.createAttendance(attendance);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAttendance(@PathVariable final Long id) throws EntityNotFoundException, PersistException {
        attendanceService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Attendance> showAttendances(SortField sortField) throws PersistException {
        sortField = SortField.DEFAULT;
        final List<Attendance> attendances1 = attendanceService.showAttendances(sortField);
        List<Attendance> attendances = new ArrayList<>();
        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
        Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
        Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");
        attendances.add(ironing);
        attendances.add(wakeup);
        attendances.add(laundry);
        attendances.add(dogWalking);
        return attendances1;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePrice(@PathVariable final Long id, @RequestBody final BigDecimal price)
        throws EntityNotFoundException, PersistException {
        attendanceService.changeAttendancePrice(id, price);
    }

    public void importAttendances() throws PersistException {
        attendanceService.importAttendances();
    }

    public void exportAttendances() throws PersistException {
        attendanceService.exportAttendances();
    }
}
