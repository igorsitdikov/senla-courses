package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.RoomHistory;

import java.util.ArrayList;
import java.util.List;

public class RoomHistoryRepository extends ARepository {
    private static List<RoomHistory> histories = new ArrayList<>();

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId((long) histories.size());
        histories.add((RoomHistory) entity);
        return entity;
    }
}
