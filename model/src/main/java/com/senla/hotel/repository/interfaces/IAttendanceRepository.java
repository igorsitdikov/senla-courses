package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface IAttendanceRepository {
    AEntity add(AEntity entity);

    void changePrice(Long id, BigDecimal price) throws EntityNotFoundException;

    void changePrice(String name, BigDecimal price);

    AEntity findById(Long id) throws EntityNotFoundException;

    AEntity findByName(String name);

    void setAttendances(List<Attendance> attendances);

    List<Attendance> getAttendances();
}
