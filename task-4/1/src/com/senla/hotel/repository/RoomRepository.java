package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;

import java.math.BigDecimal;

public class RoomRepository extends ARepository {

    private static Room[] rooms = new Room[0];

    public RoomRepository() {
    }

    public RoomRepository(final Room[] rooms) {
        this.rooms = rooms;
    }

    public Room[] getVacantRooms() {
        Room[] vacantRooms = new Room[0];
        for (final Room room : rooms) {
            if (room.getStatus() == RoomStatus.VACANT) {
                vacantRooms = arrayUtils.expandArray(Room.class, vacantRooms);
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
        rooms = arrayUtils.expandArray(Room.class, rooms);
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
        histories = arrayUtils.expandArray(RoomHistory.class, histories);
        histories[histories.length - 1] = roomHistory;
        room.setHistories(histories);
    }

    public Room[] getRooms() {
        return rooms;
    }
}
