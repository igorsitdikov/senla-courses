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

    public AttendanceRepository(final Attendance[] attendances) {
        this.attendances = attendances;
    }

    public int getTotalAttendances() {
        return attendances.length;
    }

    public Attendance[] getAttendances() {
        return attendances;
    }

    public void setAttendances(final Attendance[] attendances) {
        this.attendances = attendances;
    }
}
