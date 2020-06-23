package com.senla.hotel.service;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.service.interfaces.IRoomHistoryService;

public class RoomHistoryService implements IRoomHistoryService {
    private static RoomHistoryService roomHistoryService;
    private IRoomHistoryRepository roomHistoryRepository = RoomHistoryRepository.getInstance();

    private RoomHistoryService() {
    }

    public static RoomHistoryService getInstance() {
        if (roomHistoryService == null) {
            roomHistoryService = new RoomHistoryService();
        }
        return roomHistoryService;
    }

    @Override
    public RoomHistory create(final RoomHistory history) {
        return (RoomHistory) roomHistoryRepository.add(history);
    }

}
