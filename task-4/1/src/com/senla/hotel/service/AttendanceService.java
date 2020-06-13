package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

public class AttendanceService implements IAttendanceService {
    private AttendanceRepository attendanceRepository = new AttendanceRepository();

    @Override
    public void addAttendance(final Attendance attendance) {
        attendanceRepository.add(attendance);
    }

    @Override
    public Attendance[] getAttendances() {
        return attendanceRepository.getAttendances();
    }

    @Override
    public void showAllAttendances() {
        final Attendance[] attendances = attendanceRepository.getAttendances();
        showAttendances(attendances);
    }

    public void showAttendances(final Attendance[] attendances) {
        for (final Attendance attendance : attendances) {
            System.out.println(attendance.toString());
        }
    }

    public Attendance findAttendanceById(final Long id) throws NoSuchEntityException {
        final Attendance attendance = (Attendance) attendanceRepository.findById(id);
        if (attendance == null) {
            throw new NoSuchEntityException(String.format("No attendance with id %d%n", id));
        }
        return attendance;
    }

    public void updatePrice(final Long id, final BigDecimal price) throws NoSuchEntityException {
        final Attendance attendance = findAttendanceById(id);
        attendance.setPrice(price);
    }

    @Override
    public Attendance[] sortAttendances(final Attendance[] attendances, final Comparator<Attendance> comparator) {
        Arrays.sort(attendances, comparator);
        return attendances;
    }
}
