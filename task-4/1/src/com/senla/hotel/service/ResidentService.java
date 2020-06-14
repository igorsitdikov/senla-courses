package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.service.interfaces.IResidentService;

import java.util.Arrays;
import java.util.Comparator;

public class ResidentService implements IResidentService {
    private final ResidentRepository residentRepository = new ResidentRepository();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    @Override
    public void add(final Resident resident) {
        residentRepository.add(resident);
    }

    @Override
    public Resident[] sortResidents(final Resident[] residents, final Comparator<Resident> comparator) {
        final Resident[] result = residents.clone();
        Arrays.sort(result, comparator);
        return result;
    }

    @Override
    public int showTotalNumber() {
        return residentRepository.showTotalNumber();
    }

    @Override
    public void showResidents(final Resident[] residents) {
        for (final Resident resident : residents) {
            System.out.println(resident.toString());
        }
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
        Attendance[] attendances = resident.getHistory().getAttendances();
        attendances = (Attendance[]) attendanceRepository.add(attendances, attendance);
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
        Attendance[] attendances = resident.getHistory().getAttendances();
        attendances = (Attendance[]) attendanceRepository.add(attendances, attendance);
        resident.getHistory().setAttendances(attendances);
    }

    @Override
    public Resident[] getResidents() {
        return residentRepository.getResidents();
    }
}
