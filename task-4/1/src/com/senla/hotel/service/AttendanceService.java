package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;

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
        for (int i = 0; i < attendanceRepository.getAttendances().length; i++) {
            System.out.println(attendanceRepository.getAttendances()[i].toString());
        }
    }

    @Override
    public Attendance[] sortAttendances(final Attendance[] attendances, final Comparator<Attendance> comparator) {
        Arrays.sort(attendances, comparator);
        return attendances;
    }
}
