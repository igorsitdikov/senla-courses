package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.ResidentService;

import java.util.List;

@Singleton
public class ResidentController {
    @Autowired
    private static ResidentService residentService;

    public void createResident(final Resident resident) throws PersistException {
        residentService.createResident(resident);
    }

    public List<Resident> showResidents() throws PersistException {
        return residentService.showResidents();
    }

    public List<Resident> showResidentsSortedByName() throws PersistException {
        return residentService.showResidentsSortedByName();
    }

    public List<Resident> showResidentsSortedByCheckOutDate() throws PersistException {
        return residentService.showResidentsSortedByCheckOutDate();
    }

    public int showCountResidents() throws PersistException {
        return residentService.showCountResidents();
    }

    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
            throws EntityNotFoundException, PersistException {
        residentService.addAttendanceToResident(resident, attendance);
    }

    public void importResidents() {
        residentService.importResidents();
    }

    public void exportResidents() throws PersistException {
        residentService.exportResidents();
    }
}
