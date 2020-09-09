package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentService {

    ResidentDTO findById(Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    void addAttendanceToResident(ResidentDTO resident, AttendanceDTO attendance)
            throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws EntityNotFoundException, PersistException;

    void createResident(ResidentDTO resident) throws PersistException, EntityIsEmptyException;

    List<ResidentDTO> showResidents(SortField sortField) throws PersistException, EntityIsEmptyException;

    void importResidents() throws PersistException;

    void exportResidents() throws PersistException;

    int showCountResidents() throws PersistException;
}
