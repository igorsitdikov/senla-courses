package com.senla.hotel.controller;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    public void createResident(final ResidentDto resident) throws PersistException {
        residentService.createResident(resident);
    }

    public List<ResidentDto> showResidents(final SortField sortField) throws PersistException {
        return residentService.showResidents(sortField);
    }

    public Integer showCountResidents() throws PersistException {
        return residentService.showCountResidents();
    }

    public void addAttendanceToResident(final ResidentDto resident, final AttendanceDto attendance)
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
