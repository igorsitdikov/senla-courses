package com.senla.hotel.service;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.service.interfaces.IRoomHistoryService;

public class RoomHistoryService implements IRoomHistoryService {
    private RoomHistoryRepository roomHistoryRepository = new RoomHistoryRepository();

    @Override
    public RoomHistory create(final RoomHistory history) {
        return roomHistoryRepository.add(history);
    }

}
