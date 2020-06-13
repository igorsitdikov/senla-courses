package com.senla.hotel.repository;

import com.senla.hotel.entity.AbstractEntity;
import com.senla.hotel.entity.Attendance;

public class AttendanceRepository extends AbstractRepository {

    private static Attendance[] attendances = new Attendance[0];

    public AttendanceRepository() {
    }

    @Override
    public AbstractEntity add(final AbstractEntity entity) {
        attendances = arrayUtils.expandArray(Attendance.class, attendances);
        entity.setId((long) attendances.length);
        attendances[attendances.length - 1] = (Attendance) entity;
        return entity;
    }

    public AbstractEntity[] add(final Attendance[] attendances, final AbstractEntity entity) {
        Attendance[] result = attendances.clone();
        result = arrayUtils.expandArray(Attendance.class, result);
        entity.setId((long) result.length);
        result[result.length - 1] = (Attendance) entity;
        return result;
    }

    public AbstractEntity findById(final Long id) {
        for (final Attendance attendance : attendances) {
            if (attendance.getId().equals(id)) {
                return attendance;
            }
        }
        return null;
    }

    public Attendance[] getAttendances() {
        return attendances;
    }
}
