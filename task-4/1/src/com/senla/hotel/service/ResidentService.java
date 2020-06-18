package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.utils.comparator.ResidentCheckOutComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResidentService implements IResidentService {
    private final ResidentRepository residentRepository = new ResidentRepository();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    @Override
    public List<Resident> sortResidents(final List<Resident> residents, final Comparator<Resident> comparator) {
        final List<Resident> result = new ArrayList<>(residents);
        result.sort(comparator);
        return result;
    }

    @Override
    public Resident findById(final Long id) throws NoSuchEntityException {
        final Resident resident = residentRepository.findById(id);
        if (resident == null) {
            throw new NoSuchEntityException(String.format("No resident with id %d%n", id));
        }
        return resident;
    }

    @Override
    public void addHistoryToResident(final Long id, final RoomHistory roomHistory) throws NoSuchEntityException {
        final Resident resident = findById(id);
        residentRepository.addHistory(resident, roomHistory);
    }

    @Override
    public void addAttendanceToResident(final Long id, final Attendance attendance) throws NoSuchEntityException {
        final Resident resident = findById(id);
        List<Attendance> attendances = resident.getHistory().getAttendances();
        attendanceRepository.add(attendances, attendance);
        resident.getHistory().setAttendances(attendances);
    }

    @Override
    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
            throws NoSuchEntityException {
        final Long residentId = resident.getId();
        final Long attendanceId = attendance.getId();
        addAttendanceToResident(residentId, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Long residentId, final Long attendanceId) throws NoSuchEntityException {
        final Resident resident = findById(residentId);
        final Attendance attendance = (Attendance) attendanceRepository.findById(attendanceId);
        List<Attendance> attendances = new ArrayList<>(resident.getHistory().getAttendances());
        attendanceRepository.add(attendances, attendance);
        resident.getHistory().setAttendances(attendances);
    }

    @Override
    public void createResident(final Resident resident) {
        residentRepository.add(resident);
    }

    @Override
    public List<Resident> showResidents() {
        return residentRepository.getResidents();
    }

    @Override
    public List<Resident> showResidentsSortedByName() {
        final List<Resident> residents = showResidents();
        return sortResidents(residents, new ResidentFullNameComparator());
    }

    @Override
    public List<Resident> showResidentsSortedByCheckOutDate() {
        final List<Resident> residents = showResidents();
        return sortResidents(residents, new ResidentCheckOutComparator());
    }

    @Override
    public int showCountResidents() {
        return residentRepository.showTotalNumber();
    }

}
