package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;

import java.util.List;

public interface IRoomHistoryRepository {
    AEntity findById(Long id);

    void addAttendance(Long id, Attendance attendance);

    AEntity add(AEntity entity);

    void setHistories(List<RoomHistory> roomHistories);

    List<RoomHistory> getHistories();
}
