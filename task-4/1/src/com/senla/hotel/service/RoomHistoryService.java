package com.senla.hotel.service;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.RoomHistoryRepository;

public class RoomHistoryService {
    private RoomHistoryRepository roomHistoryRepository = new RoomHistoryRepository();

    public RoomHistory create(final RoomHistory history) {
        return roomHistoryRepository.add(history);
    }

}
