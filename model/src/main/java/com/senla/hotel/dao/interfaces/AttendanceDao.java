package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface AttendanceDao extends GenericDao<Attendance, Long> {
    List<Attendance> getAllAttendancesByHistoryId(final Long id) throws PersistException;
}
