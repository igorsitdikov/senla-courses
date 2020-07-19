package com.senla.hotel.repository;

import com.senla.annotation.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.interfaces.IRoomRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomRepository implements IRoomRepository {
    private static List<Room> rooms = new ArrayList<>();
    private static Long counter = 0L;

    @Override
    public List<Room> getVacantRooms() {
        final List<Room> vacantRooms = new ArrayList<>();
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
    public AEntity findById(final Long id) throws EntityNotFoundException {
        for (final Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        throw new EntityNotFoundException(String.format("No room with id %d%n", id));
    }

    @Override
    public AEntity findByRoomNumber(final Integer number) throws EntityNotFoundException {
        for (final Room room : rooms) {
            if (room.getNumber().equals(number)) {
                return room;
            }
        }
        throw new EntityNotFoundException(String.format("No room with number %d%n", number));
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        final Room room = (Room) findById(id);
        room.setPrice(price);
    }

    @Override
    public void changePrice(final Integer number, final BigDecimal price) throws EntityNotFoundException {
        final Room room = (Room) findByRoomNumber(number);
        room.setPrice(price);
    }

    @Override
    public void addHistory(final Room room, final RoomHistory roomHistory) {
        final List<RoomHistory> histories = room.getHistories();
        histories.add(roomHistory);
        room.setHistories(histories);
    }

    @Override
    public void setRooms(final List<Room> rooms) {
        RoomRepository.rooms = new ArrayList<>(rooms);
    }

    @Override
    public void setCounter(final Long counter) {
        RoomRepository.counter = counter;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }
}
