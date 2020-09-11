package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<Room> showVacantRoomsOnDate(LocalDate date) throws PersistException;

    Room findById(Long id) throws EntityNotFoundException, PersistException;

    Room findByNumber(Integer number) throws EntityNotFoundException, PersistException;

    List<Resident> showLastResidents(Long id, Integer number) throws EntityNotFoundException, PersistException;

    void importRooms() throws PersistException;

    void exportRooms() throws PersistException;

    List<Resident> showLastResidents(Room room, Integer number) throws EntityNotFoundException, PersistException;

    Long countVacantRooms() throws PersistException;

    void changeRoomPrice(Integer number, BigDecimal price) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Long id, RoomStatus status) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Integer number, RoomStatus status) throws EntityNotFoundException, PersistException;

    void addRoom(Room room) throws PersistException;

    List<Room> showAll(SortField sortField) throws PersistException;

    List<Room> showVacant(SortField sortField) throws PersistException;

    Room showRoomDetails(Integer number) throws EntityNotFoundException, PersistException;
}
