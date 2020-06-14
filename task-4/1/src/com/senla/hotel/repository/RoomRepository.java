package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;

import java.math.BigDecimal;
import java.util.Arrays;

public class RoomRepository extends ARepository {

    private static Room[] rooms = new Room[0];

    public RoomRepository() {
    }

    public RoomRepository(final Room[] rooms) {
        this.rooms = rooms;
    }

    public Room[] castArray(final AEntity[] array) {
        return Arrays.copyOf(array, array.length, Room[].class);
    }

    public Room[] getVacantRooms() {
        Room[] vacantRooms = new Room[0];
        for (final Room room : rooms) {
            if (room.getStatus() == RoomStatus.VACANT) {
                final AEntity[] entities = arrayUtils.expandArray(vacantRooms);
                vacantRooms = castArray(entities);
                vacantRooms[vacantRooms.length - 1] = room;
            }
        }
        return vacantRooms;
    }

    public int countVacantRooms() {
        return getVacantRooms().length;
    }

    @Override
    public AEntity add(final AEntity room) {
        final AEntity[] entities = arrayUtils.expandArray(rooms);
        rooms = castArray(entities);
        room.setId((long) rooms.length);
        rooms[rooms.length - 1] = (Room) room;
        return room;
    }

    public AEntity findById(final Long id) {
        for (final Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public AEntity findByRoomNumber(final Integer number) {
        for (final Room room : rooms) {
            if (room.getNumber().equals(number)) {
                return room;
            }
        }
        return null;
    }

    public void updatePrice(final Long id, final BigDecimal price) {
        final Room room = (Room) findById(id);
        room.setPrice(price);
    }

    public void updatePrice(final Integer number, final BigDecimal price) {
        final Room room = (Room) findByRoomNumber(number);
        room.setPrice(price);
    }

    public void addHistory(final Room room, final RoomHistory roomHistory) {
        RoomHistory[] histories = room.getHistories();
        final RoomHistoryRepository roomHistoryRepository = new RoomHistoryRepository();
        final AEntity[] entities = arrayUtils.expandArray(histories);
        histories = roomHistoryRepository.castArray(entities);
        histories[histories.length - 1] = roomHistory;
        room.setHistories(histories);
    }

    public Room[] getRooms() {
        return rooms;
    }
}
