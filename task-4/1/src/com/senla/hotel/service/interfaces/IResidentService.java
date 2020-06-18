package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.util.Comparator;

public interface IResidentService {

    Resident[] sortResidents(Resident[] residents, Comparator<Resident> comparator);

    Resident findById(Long id) throws NoSuchEntityException;

    void addHistoryToResident(Long id, RoomHistory roomHistory) throws NoSuchEntityException;

    void addAttendanceToResident(Long id, Attendance attendance) throws NoSuchEntityException;

    void addAttendanceToResident(Resident resident, Attendance attendance)
        throws NoSuchEntityException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws NoSuchEntityException;

    void createResident(Resident resident);

    Resident[] showResidents();

    Resident[] showResidentsSortedByName();

    Resident[] showResidentsSortedByCheckOutDate();

    int showCountResidents();
}
