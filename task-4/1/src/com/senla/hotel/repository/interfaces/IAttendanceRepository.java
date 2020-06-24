package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;

import java.math.BigDecimal;
import java.util.List;

public interface IAttendanceRepository {
    AEntity add(AEntity entity);

    void changePrice(Long id, BigDecimal price);

    void changePrice(String name, BigDecimal price);

    List<AEntity> add(List<Attendance> attendances, AEntity entity);

    AEntity findById(Long id);

    AEntity findByName(String name);

    void setAttendances(List<Attendance> attendances);

    List<Attendance> getAttendances();
}
