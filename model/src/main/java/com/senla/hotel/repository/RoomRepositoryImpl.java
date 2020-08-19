package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.interfaces.RoomRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomRepositoryImpl implements RoomRepository {
    @Autowired
    private RoomDao roomDao;
    private static List<Room> rooms = new ArrayList<>();
    private static Long counter = 0L;

    @Override
    public List<Room> getVacantRooms() {
        try {
            return roomDao.getVacantRooms();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
//        final List<Room> vacantRooms = new ArrayList<>();
//        for (final Room room : rooms) {
//            if (room.getStatus() == RoomStatus.VACANT) {
//                vacantRooms.add(room);
//            }
//        }
//        return vacantRooms;
    }

    @Override
    public int countVacantRooms() {
        return getVacantRooms().size();
    }

    @Override
    public AEntity add(final AEntity room) {
        try {
            return roomDao.create((Room)room);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
//        return null;
//        room.setId(++counter);
//        rooms.add((Room) room);
//        return room;
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return roomDao.getById(id);
        } catch (PersistException e) {
            e.printStackTrace();
        }
//        for (final Room room : rooms) {
//            if (room.getId().equals(id)) {
//                return room;
//            }
//        }
        throw new EntityNotFoundException(String.format("No room with id %d%n", id));
    }

    @Override
    public AEntity findByRoomNumber(final Integer number) throws EntityNotFoundException {
        try {
            return roomDao.getRoomByNumber(number);
        } catch (PersistException e) {
            e.printStackTrace();
        }
//        for (final Room room : rooms) {
//            if (room.getNumber().equals(number)) {
//                return room;
//            }
//        }
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
        RoomRepositoryImpl.rooms = new ArrayList<>(rooms);
    }

    @Override
    public void setCounter(final Long counter) {
        RoomRepositoryImpl.counter = counter;
    }

    @Override
    public List<Room> getRooms() {
        try {
            return roomDao.getAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }
}
