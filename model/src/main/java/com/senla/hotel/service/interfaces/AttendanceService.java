package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public interface AttendanceService {
    Attendance findById(Long id) throws EntityNotFoundException, PersistException;

    void createAttendance(Attendance attendance) throws EntityAlreadyExistsException, PersistException;

    List<Attendance> showAttendances(SortField sortField) throws PersistException;

    void changeAttendancePrice(Long id, BigDecimal price) throws EntityNotFoundException, PersistException;

    void delete(Long id) throws EntityNotFoundException, PersistException;

    void importAttendances();

    void exportAttendances() throws PersistException;
}
