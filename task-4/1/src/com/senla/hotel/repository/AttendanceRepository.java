package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository implements IAttendanceRepository {
    private static AttendanceRepository attendanceRepository;
    private static List<Attendance> attendances = new ArrayList<>();
    private static Long counter = 0L;

    private AttendanceRepository() {
    }

    public static AttendanceRepository getInstance() {
        if (attendanceRepository == null) {
            attendanceRepository = new AttendanceRepository();
        }
        return attendanceRepository;
    }

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId(++counter);
        attendances.add((Attendance) entity);
        return entity;
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) {
        final Attendance attendance = (Attendance) findById(id);
        attendance.setPrice(price);
    }

    @Override
    public void changePrice(final String name, final BigDecimal price) {
        final Attendance attendance = (Attendance) findByName(name);
        attendance.setPrice(price);
    }

    @Override
    public List<AEntity> add(final List<Attendance> attendances, final AEntity entity) {
        final List<AEntity> result = new ArrayList<>(attendances);
        result.add(entity);
        return result;
    }

    @Override
    public AEntity findById(final Long id) {
        for (final Attendance attendance : attendances) {
            if (attendance.getId().equals(id)) {
                return attendance;
            }
        }
        return null;
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
        AttendanceRepository.attendances = new ArrayList<>(attendances);
    }
    
    @Override
    public List<Attendance> getAttendances() {
        return attendances;
    } 

}
