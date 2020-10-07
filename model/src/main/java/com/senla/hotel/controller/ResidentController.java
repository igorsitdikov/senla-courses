package com.senla.hotel.controller;

import com.senla.hotel.dto.AmountDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
@RequestMapping("/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(final ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createResident(@RequestBody final ResidentDto resident) throws PersistException {
        residentService.createResident(resident);
    }

    @GetMapping(value = "/sort/{sortField}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResidentDto> showResidents(@PathVariable("sortField") final SortField sortField) throws PersistException {
        return residentService.showResidents(sortField);
    }

    @GetMapping(value = "/amount")
    @ResponseStatus(HttpStatus.OK)
    public AmountDto showCountResidents() throws PersistException {
        return new AmountDto(residentService.showCountResidents());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void addAttendanceToResident(@RequestParam("residentId") final Long residentId,
                                        @RequestParam("attendanceId") final Long attendanceId)
            throws EntityNotFoundException, PersistException {
        residentService.addAttendanceToResident(residentId, attendanceId);
    }

    @GetMapping(value = "/import")
    @ResponseStatus(HttpStatus.OK)
    public void importResidents() throws PersistException {
        residentService.importResidents();
    }

    @GetMapping(value = "/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportResidents() throws PersistException {
        residentService.exportResidents();
    }
}
