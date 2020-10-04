package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentService {

    ResidentDto findById(Long id) throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(ResidentDto resident, AttendanceDto attendance)
            throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws EntityNotFoundException, PersistException;

    void createResident(ResidentDto resident) throws PersistException;

    List<ResidentDto> showResidents(SortField sortField) throws PersistException;

    void importResidents() throws PersistException;

    void exportResidents() throws PersistException;

    Integer showCountResidents() throws PersistException;
}
