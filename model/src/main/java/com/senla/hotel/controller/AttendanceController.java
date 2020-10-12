package com.senla.hotel.controller;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(final AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAttendance(@RequestBody final AttendanceDto attendance) throws EntityAlreadyExistsException, PersistException {
        attendanceService.createAttendance(attendance);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAttendance(@PathVariable final Long id) throws EntityNotFoundException, PersistException {
        attendanceService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AttendanceDto findById(@PathVariable final Long id) throws EntityNotFoundException, PersistException {
        return attendanceService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAttendance(@RequestBody final AttendanceDto attendanceDto) throws PersistException {
        attendanceService.updateAttendance(attendanceDto);
    }

    @GetMapping(value = "/sort/{sortField}")
    @ResponseStatus(HttpStatus.OK)
    public List<AttendanceDto> showAttendances(@PathVariable("sortField") SortField sortField) throws PersistException {
        return attendanceService.showAttendances(sortField);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePrice(@PathVariable final Long id, @RequestBody final PriceDto price)
        throws EntityNotFoundException, PersistException {
        attendanceService.changeAttendancePrice(id, price.getPrice());
    }

    @GetMapping(value = "/import")
    @ResponseStatus(HttpStatus.OK)
    public void importAttendances() throws PersistException {
        attendanceService.importAttendances();
    }

    @GetMapping(value = "/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportAttendances() throws PersistException {
        attendanceService.exportAttendances();
    }
}
