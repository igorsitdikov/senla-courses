package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public interface IAttendanceService {

    Attendance findByName(String name);

    List<Attendance> sortAttendances(List<Attendance> attendances, Comparator<Attendance> comparator);

    Attendance findById(Long id) throws EntityNotFoundException;

    void createAttendance(Attendance attendance) throws EntityAlreadyExistsException;

    List<Attendance> showAttendances();

    List<Attendance> showAttendancesSortedByName();

    List<Attendance> showAttendancesSortedByPrice();

    List<AEntity> add(List<Attendance> attendances, AEntity entity);

    void changeAttendancePrice(Long id, BigDecimal price) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    void changeAttendancePrice(String name, BigDecimal price);

    void importAttendances();

    void exportAttendances();
}
