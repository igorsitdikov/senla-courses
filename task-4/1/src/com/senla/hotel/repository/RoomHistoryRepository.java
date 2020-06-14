package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.RoomHistory;

import java.util.Arrays;

public class RoomHistoryRepository extends ARepository {
    private static RoomHistory[] histories = new RoomHistory[0];

    public RoomHistory[] castArray(final AEntity[] array) {
        return Arrays.copyOf(array, array.length, RoomHistory[].class);
    }

    @Override
    public AEntity add(final AEntity entity) {
        final AEntity[] entities = arrayUtils.expandArray(histories);
        histories = castArray(entities);
        entity.setId((long) histories.length);
        histories[histories.length - 1] = (RoomHistory) entity;
        return entity;
    }
}
