package com.senla.hotel.repository;

import com.senla.anntotaion.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceRepository implements IAttendanceRepository {
    private static List<Attendance> attendances = new LinkedList<>();
    private static Long counter = 0L;

    @Override
    public AEntity add(final AEntity entity) throws EntityAlreadyExistsException {
        if (!AttendanceRepository.attendances.contains(entity)) {
            entity.setId(++counter);
            attendances.add((Attendance) entity);
            return entity;
        }
        throw new EntityAlreadyExistsException("Attendance already exists");
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        final Attendance attendance = (Attendance) findById(id);
        attendance.setPrice(price);
    }

    @Override
    public void changePrice(final String name, final BigDecimal price) {
        final Attendance attendance = (Attendance) findByName(name);
        attendance.setPrice(price);
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        for (final Attendance attendance : attendances) {
            if (attendance.getId().equals(id)) {
                return attendance;
            }
        }
        throw new EntityNotFoundException(String.format("No attendance with id %d%n", id));
    }

    @Override
    public AEntity findByName(final String name) {
        for (final Attendance attendance : attendances) {
            if (attendance.getName().equals(name)) {
                return attendance;
            }
        }
        return null;
    }

    @Override
    public void setAttendances(final List<Attendance> attendances) {
        AttendanceRepository.attendances.clear();
        AttendanceRepository.attendances = new ArrayList<>(attendances);
    }

    @Override
    public void deleteAttendance(final Long id) throws EntityNotFoundException {
        final Attendance attendance = (Attendance) findById(id);
        AttendanceRepository.attendances.remove(attendance);
    }

    @Override
    public List<Attendance> getAttendances() {
        return attendances;
    }

    @Override
    public void setCounter(final Long counter) {
        AttendanceRepository.counter = counter;
    }

}
