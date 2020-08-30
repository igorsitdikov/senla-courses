package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.PersistException;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends GenericDao<Room, Long> {

    List<Room> getVacantRooms() throws PersistException;

    Room findByNumber(Integer number) throws PersistException;

    List<Room> getVacantOnDate(LocalDate date) throws PersistException;
}