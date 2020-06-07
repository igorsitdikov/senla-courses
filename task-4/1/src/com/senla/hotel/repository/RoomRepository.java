package com.senla.hotel.repository;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.type.RoomStatus;

public class RoomRepository extends AbstractRepository<Room> {

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

    public int countEmptyRooms() {
        return getVacantRooms().length;
    }

    public Room add(final Room room) {
        rooms = arrayUtils.expandArray(Room.class, rooms);
        room.setId((long) rooms.length);
        rooms[rooms.length - 1] = room;
        return room;
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
