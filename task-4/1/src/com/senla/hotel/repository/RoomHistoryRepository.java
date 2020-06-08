package com.senla.hotel.repository;

import com.senla.hotel.entity.AbstractEntity;
import com.senla.hotel.entity.RoomHistory;

public class RoomHistoryRepository extends AbstractRepository {
    private static RoomHistory[] histories = new RoomHistory[0];

    @Override
    public AbstractEntity add(final AbstractEntity entity) {
        histories = arrayUtils.expandArray(RoomHistory.class, histories);
        entity.setId((long) histories.length);
        histories[histories.length - 1] = (RoomHistory) entity;
        return entity;
    }

    public static RoomHistory[] getHistories() {
        return histories;
    }
}
