package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.repository.interfaces.IRoomRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private static RoomRepository roomRepository;
    private static List<Room> rooms = new ArrayList<>();
    private static Long counter = 0L;

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        if (roomRepository == null) {
            roomRepository = new RoomRepository();
        }
        return roomRepository;
    }

    @Override
    public List<Room> getVacantRooms() {
        List<Room> vacantRooms = new ArrayList<>();
        for (final Room room : rooms) {
            if (room.getStatus() == RoomStatus.VACANT) {
                vacantRooms.add(room);
            }
        }
        return vacantRooms;
    }

    @Override
    public int countVacantRooms() {
        return getVacantRooms().size();
    }

    @Override
    public AEntity add(final AEntity room) {
        room.setId(++counter);
        rooms.add((Room) room);
        return room;
    }

    @Override
    public AEntity findById(final Long id) {
        for (final Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public AEntity findByRoomNumber(final Integer number) {
        for (final Room room : rooms) {
            if (room.getNumber().equals(number)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) {
        final Room room = (Room) findById(id);
        room.setPrice(price);
    }

    @Override
    public void changePrice(final Integer number, final BigDecimal price) {
        final Room room = (Room) findByRoomNumber(number);
        room.setPrice(price);
    }

    @Override
    public void addHistory(final Room room, final RoomHistory roomHistory) {
        List<RoomHistory> histories = room.getHistories();
        histories.add(roomHistory);
        room.setHistories(histories);
    }

    @Override
    public void setRooms(final List<Room> rooms) {
        RoomRepository.rooms = new ArrayList<>(rooms);
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }
}
