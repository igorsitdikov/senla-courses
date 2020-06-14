package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.RoomHistory;

public class RoomHistoryRepository extends ARepository {
    private static RoomHistory[] histories = new RoomHistory[0];

    @Override
    public AEntity add(final AEntity entity) {
        histories = arrayUtils.expandArray(RoomHistory.class, histories);
        entity.setId((long) histories.length);
        histories[histories.length - 1] = (RoomHistory) entity;
        return entity;
    }
}
