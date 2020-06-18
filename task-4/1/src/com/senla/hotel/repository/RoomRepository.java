package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository extends ARepository {

    private static List<Room> rooms = new ArrayList<>();

    public RoomRepository() {
    }

    public RoomRepository(final List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getVacantRooms() {
        List<Room> vacantRooms = new ArrayList<>();
        for (final Room room : rooms) {
            if (room.getStatus() == RoomStatus.VACANT) {
                vacantRooms.add(room);
            }
        }
        return vacantRooms;
    }

    public int countVacantRooms() {
        return getVacantRooms().size();
    }

    @Override
    public AEntity add(final AEntity room) {
        room.setId((long) rooms.size());
        rooms.add((Room) room);
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

    public void changePrice(final Long id, final BigDecimal price) {
        final Room room = (Room) findById(id);
        room.setPrice(price);
    }

    public void changePrice(final Integer number, final BigDecimal price) {
        final Room room = (Room) findByRoomNumber(number);
        room.setPrice(price);
    }

    public void addHistory(final Room room, final RoomHistory roomHistory) {
        List<RoomHistory> histories = room.getHistories();
        histories.add(roomHistory);
        room.setHistories(histories);
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
