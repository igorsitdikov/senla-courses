package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.util.List;

public interface AttendanceRepository {
    AEntity create(AEntity entity) throws EntityAlreadyExistsException;

//    void update(Long id, BigDecimal price) throws EntityNotFoundException;
    void update(Attendance attendance) throws EntityNotFoundException;

    AEntity findById(Long id) throws EntityNotFoundException;

    void setAttendances(List<Attendance> attendances);

    void delete(Attendance attendance) throws EntityNotFoundException;

    List<Attendance> getAll();
}
