package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;

import java.util.ArrayList;
import java.util.List;

public class RoomHistoryRepository implements IRoomHistoryRepository {
    private static RoomHistoryRepository roomHistoryRepository;
    private static List<RoomHistory> histories = new ArrayList<>();

    private RoomHistoryRepository() {
    }

    public static RoomHistoryRepository getInstance() {
        if (roomHistoryRepository == null) {
            roomHistoryRepository = new RoomHistoryRepository();
        }
        return roomHistoryRepository;
    }

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId((long) histories.size());
        histories.add((RoomHistory) entity);
        return entity;
    }

    @Override
    public List<RoomHistory> getHistories() {
        return histories;
    }
}
