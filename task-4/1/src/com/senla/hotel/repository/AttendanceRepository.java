package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;

import java.math.BigDecimal;

public class AttendanceRepository extends ARepository {

    private static Attendance[] attendances = new Attendance[0];

    public AttendanceRepository() {
    }

    @Override
    public AEntity add(final AEntity entity) {
        attendances = arrayUtils.expandArray(Attendance.class, attendances);
        entity.setId((long) attendances.length);
        attendances[attendances.length - 1] = (Attendance) entity;
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

    public AEntity[] add(final Attendance[] attendances, final AEntity entity) {
        Attendance[] result = attendances.clone();
        result = arrayUtils.expandArray(Attendance.class, result);
        entity.setId((long) result.length);
        result[result.length - 1] = (Attendance) entity;
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

    public Attendance[] getAttendances() {
        return attendances;
    }
}
