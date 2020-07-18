package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.util.List;

public interface IRoomHistoryRepository {
    AEntity findById(Long id) throws EntityNotFoundException;

    void addAttendance(Long id, Attendance attendance) throws EntityNotFoundException;

    AEntity add(AEntity entity);

    void setHistories(List<RoomHistory> roomHistories);

    void setCounter(Long counter);

    List<RoomHistory> getHistories();
}