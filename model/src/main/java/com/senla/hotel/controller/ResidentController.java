package com.senla.hotel.controller;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.interfaces.IResidentService;

import java.util.List;

@Singleton
public class ResidentController {
    @Autowired
    private static IResidentService residentService;

    public void createResident(final Resident resident) {
        residentService.createResident(resident);
    }

    public List<Resident> showResidents() {
        return residentService.showResidents();
    }

    public List<Resident> showResidentsSortedByName() {
        return residentService.showResidentsSortedByName();
    }

    public List<Resident> showResidentsSortedByCheckOutDate() {
        return residentService.showResidentsSortedByCheckOutDate();
    }

    public int showCountResidents() {
        return residentService.showCountResidents();
    }

    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
        throws EntityNotFoundException {
        residentService.addAttendanceToResident(resident, attendance);
    }

    public void importResidents() {
        residentService.importResidents();
    }

    public void exportResidents() {
        residentService.exportResidents();
    }
}