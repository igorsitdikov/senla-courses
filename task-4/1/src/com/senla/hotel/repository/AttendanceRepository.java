package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository extends ARepository {

    private static List<Attendance> attendances = new ArrayList<>();

    public AttendanceRepository() {
    }

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId((long) attendances.size());
        attendances.add((Attendance) entity);
        return entity;
    }

    public void updatePrice(final Long id, final BigDecimal price) {
        final Attendance attendance = (Attendance) findById(id);
        attendance.setPrice(price);
    }

    public void updatePrice(final String name, final BigDecimal price) {
        final Attendance attendance = (Attendance) findByName(name);
        attendance.setPrice(price);
    }

    public List<AEntity> add(final List<Attendance> attendances, final AEntity entity) {
        List<AEntity> result = new ArrayList<>(attendances);
        entity.setId((long) result.size());
        result.add(entity);
        return result;
    }

    public AEntity findById(final Long id) {
        for (final Attendance attendance : attendances) {
            if (attendance.getId().equals(id)) {
                return attendance;
            }
        }
        return null;
    }

    public AEntity findByName(final String name) {
        for (final Attendance attendance : attendances) {
            if (attendance.getName().equals(name)) {
                return attendance;
            }
        }
        return null;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
