package com.senla.hotel.controller;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.interfaces.IResidentService;

import java.util.List;

public class ResidentController {
    private static ResidentController residentController;
    private static IResidentService residentService;

    private ResidentController() {
        residentService = ResidentService.getInstance();
    }

    public static ResidentController getInstance() {
        if (residentController == null) {
            residentController = new ResidentController();
        }
        return residentController;
    }

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

    public void addAttendanceToResident(final Resident resident, final Attendance attendance) throws EntityNotFoundException {
        residentService.addAttendanceToResident(resident, attendance);
    }

    public void importResidents() {
        residentService.importResidents();
    }

    public void exportResidents() {
        residentService.exportResidents();
    }
}
