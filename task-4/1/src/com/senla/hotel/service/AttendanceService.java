package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class AttendanceService implements IAttendanceService {
    private AttendanceRepository attendanceRepository = new AttendanceRepository();

    public Attendance findAttendanceById(final Long id) throws NoSuchEntityException {
        final Attendance attendance = (Attendance) attendanceRepository.findById(id);
        if (attendance == null) {
            throw new NoSuchEntityException(String.format("No attendance with id %d%n", id));
        }
        return attendance;
    }

    @Override
    public List<Attendance> sortAttendances(final List<Attendance> attendances, final Comparator<Attendance> comparator) {
        attendances.sort(comparator);
        return attendances;
    }

    @Override
    public void createAttendance(final Attendance attendance) {
        attendanceRepository.add(attendance);
    }

    @Override
    public List<Attendance> showAttendances() {
        return attendanceRepository.getAttendances();
    }

    @Override
    public List<Attendance> showAttendancesSortedByName() {
        final List<Attendance> attendances = showAttendances();
        return sortAttendances(attendances, new AttendanceNameComparator());
    }

    @Override
    public List<Attendance> showAttendancesSortedByPrice() {
        final List<Attendance> attendances = showAttendances();
        return sortAttendances(attendances, new AttendancePriceComparator());
    }

    @Override
    public void changeAttendancePrice(final Long id, final BigDecimal price) {
        attendanceRepository.updatePrice(id, price);
    }

    @Override
    public void changeAttendancePrice(final String name, final BigDecimal price) {
        attendanceRepository.updatePrice(name, price);
    }
}