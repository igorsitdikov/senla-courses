package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface RoomDao extends GenericDao<Room, Long> {
    List<Room> getVacantRooms() throws PersistException;

    Room getRoomByNumber(Integer number) throws PersistException;
}
