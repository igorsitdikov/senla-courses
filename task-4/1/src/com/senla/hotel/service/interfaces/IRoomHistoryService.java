package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.util.List;

public interface IRoomHistoryService {
    RoomHistory create(RoomHistory history);

    RoomHistory findById(Long id) throws EntityNotFoundException;

    void importHistories();

    void exportHistories();

    List<RoomHistory> showHistories();
}
