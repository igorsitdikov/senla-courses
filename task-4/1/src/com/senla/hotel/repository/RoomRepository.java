package com.senla.hotel.repository;

import com.senla.hotel.entity.AbstractEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;

public class RoomRepository extends AbstractRepository {

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

    public AbstractEntity add(final AbstractEntity room) {
        rooms = arrayUtils.expandArray(Room.class, rooms);
        room.setId((long) rooms.length);
        rooms[rooms.length - 1] = (Room) room;
        return room;
    }

    public Room findById(final Long id) {
        for (final Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public void addHistory(final Room room, final RoomHistory roomHistory) {
        RoomHistory[] histories = room.getHistories();
        histories = arrayUtils.expandArray(RoomHistory.class, histories);
        histories[histories.length - 1] = roomHistory;
        room.setHistories(histories);
    }

    public int getTotalRooms() {
        return rooms.length;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(final Room[] rooms) {
        this.rooms = rooms;
    }
}
