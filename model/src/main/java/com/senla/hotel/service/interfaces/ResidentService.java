package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentService {
    Resident findById(Long id) throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(Resident resident, Attendance attendance)
            throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws EntityNotFoundException, PersistException;

    void createResident(Resident resident) throws PersistException;

    List<Resident> showResidents(SortField sortField) throws PersistException;

    void importResidents();

    void exportResidents() throws PersistException;

    int showCountResidents() throws PersistException;
}
