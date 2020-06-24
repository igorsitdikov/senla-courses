package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.util.List;

public interface IRoomHistoryService {
    RoomHistory create(RoomHistory history);

    RoomHistory findById(Long id) throws NoSuchEntityException;

    void importHistories(List<RoomHistory> histories);

    void exportHistories();

    List<RoomHistory> showHistories();
}
