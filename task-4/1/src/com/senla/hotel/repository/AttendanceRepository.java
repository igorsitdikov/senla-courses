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

    public AbstractEntity[] add(Attendance[] attendances, final AbstractEntity entity) {
        Attendance[] at = attendances.clone();
        at = arrayUtils.expandArray(Attendance.class, at);
        entity.setId((long) at.length);
        at[at.length - 1] = (Attendance) entity;
        return at;
    }

    public Attendance[] getAttendances() {
        return attendances;
    }
}
