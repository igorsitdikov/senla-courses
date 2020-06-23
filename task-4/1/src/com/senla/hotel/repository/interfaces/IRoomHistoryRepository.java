package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.RoomHistory;

import java.util.List;

public interface IRoomHistoryRepository {
    AEntity add(AEntity entity);

    List<RoomHistory> getHistories();
}
