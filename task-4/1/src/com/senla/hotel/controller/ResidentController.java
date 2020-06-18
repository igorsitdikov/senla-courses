package com.senla.hotel.controller;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.interfaces.IResidentService;

public final class ResidentController {
    private static ResidentController residentController;
    private static IResidentService residentService;

    public ResidentController() {
        residentService = new ResidentService();
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

    public Resident[] showResidents() {
        return residentService.showResidents();
    }

    public Resident[] showResidentsSortedByName() {
        return residentService.showResidentsSortedByName();
    }

    public Resident[] showResidentsSortedByCheckOutDate() {
        return residentService.showResidentsSortedByCheckOutDate();
    }

    public int showCountResidents() {
        return residentService.showCountResidents();
    }

    public void addAttendanceToResident(final Resident resident, final Attendance attendance) throws NoSuchEntityException {
        residentService.addAttendanceToResident(resident, attendance);
    }
}
