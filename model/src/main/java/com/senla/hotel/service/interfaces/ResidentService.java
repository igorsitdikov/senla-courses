package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.Comparator;
import java.util.List;

public interface ResidentService {

    List<Resident> sortResidents(List<Resident> residents, Comparator<Resident> comparator);

    Resident findById(Long id) throws EntityNotFoundException;

    void addAttendanceToResident(Resident resident, Attendance attendance)
            throws EntityNotFoundException, PersistException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws EntityNotFoundException, PersistException;

    void createResident(Resident resident);

    List<Resident> showResidents();

    List<Resident> showResidentsSortedByName();

    List<Resident> showResidentsSortedByCheckOutDate();

    void importResidents();

    void exportResidents();

    int showCountResidents();
}
