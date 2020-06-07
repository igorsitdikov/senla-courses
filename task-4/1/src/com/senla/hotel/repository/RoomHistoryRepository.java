package com.senla.hotel.repository;

import com.senla.hotel.entity.RoomHistory;

public class RoomHistoryRepository extends AbstractRepository<RoomHistory> {
    private static RoomHistory[] histories = new RoomHistory[0];

    @Override
    public RoomHistory add(final RoomHistory entity) {
        histories = arrayUtils.expandArray(RoomHistory.class, histories);
        entity.setId((long) histories.length);
        histories[histories.length - 1] = entity;
        return entity;
    }

    public static RoomHistory[] getHistories() {
        return histories;
    }
}
