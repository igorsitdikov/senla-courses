package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.util.Comparator;
import java.util.List;

public interface IResidentService {

    List<Resident> sortResidents(List<Resident> residents, Comparator<Resident> comparator);

    Resident findById(Long id) throws NoSuchEntityException;

    void addHistoryToResident(Long id, RoomHistory roomHistory) throws NoSuchEntityException;

    void addAttendanceToResident(Long id, Attendance attendance) throws NoSuchEntityException;

    void addAttendanceToResident(Resident resident, Attendance attendance)
            throws NoSuchEntityException;

    void addAttendanceToResident(Long residentId, Long attendanceId) throws NoSuchEntityException;

    void createResident(Resident resident);

    List<Resident> showResidents();

    List<Resident> showResidentsSortedByName();

    List<Resident> showResidentsSortedByCheckOutDate();

    void importResidents();

    void exportResidents();

    int showCountResidents();
}
