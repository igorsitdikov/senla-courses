package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.repository.interfaces.RoomRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomRepositoryImpl implements RoomRepository {
    @Autowired
    private RoomDao roomDao;
    private static List<Room> rooms = new ArrayList<>();

    @Override
    public List<Room> getVacantRooms() {
        try {
            return roomDao.getVacantRooms();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
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
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return roomDao.findById(id);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("No room with id %d%n", id));
    }

    @Override
    public AEntity findByNumber(final Integer number) throws EntityNotFoundException {
        try {
            return roomDao.findByNumber(number);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("No room with number %d%n", number));
    }

    @Override
    public void update(final Room room) {
        try {
            roomDao.update(room);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        final Room room = (Room) findById(id);
        room.setPrice(price);
    }

    @Override
    public void changePrice(final Integer number, final BigDecimal price) throws EntityNotFoundException {
        final Room room = (Room) findByNumber(number);
        room.setPrice(price);
    }

    @Override
    public void setRooms(final List<Room> rooms) {
        RoomRepositoryImpl.rooms = new ArrayList<>(rooms);
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
