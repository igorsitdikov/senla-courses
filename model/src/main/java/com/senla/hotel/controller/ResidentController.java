package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.ResidentService;

import java.util.List;

@Singleton
public class ResidentController {

    @Autowired
    private static ResidentService residentService;

    public void createResident(final ResidentDTO resident) throws PersistException, EntityIsEmptyException {
        residentService.createResident(resident);
    }

    public List<ResidentDTO> showResidents(final SortField sortField) throws PersistException, EntityIsEmptyException {
        return residentService.showResidents(sortField);
    }

    public int showCountResidents() throws PersistException {
        return residentService.showCountResidents();
    }

    public void addAttendanceToResident(final ResidentDTO resident, final AttendanceDTO attendance)
            throws EntityNotFoundException, PersistException {
        residentService.addAttendanceToResident(resident, attendance);
    }

    public void importResidents() throws PersistException {
        residentService.importResidents();
    }

    public void exportResidents() throws PersistException {
        residentService.exportResidents();
    }
}
