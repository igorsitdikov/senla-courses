package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

public interface IRoomService {

    void add(Room room);

    void showRooms(Room[] rooms);

    String getRoomsAsString(Room[] rooms);

    void addHistoryToRoom(Long id, RoomHistory history) throws NoSuchEntityException;

    void updateCheckOutHistory(Long id, RoomHistory history, LocalDate checkOut)
        throws NoSuchEntityException;

    void updatePrice(Long id, BigDecimal price);

    void updatePrice(Integer number, BigDecimal price);

    Room[] sortRooms(Room[] rooms, Comparator<Room> comparator);

    Room findRoomById(Long id) throws NoSuchEntityException;

    Room findRoomByRoomNumber(Integer number) throws NoSuchEntityException;

    Room[] vacantOnDate(LocalDate date);

    Room update(Long id, RoomStatus roomStatus);

    Room update(Integer number, RoomStatus roomStatus);

    int countVacantRooms();

    Room[] getAllRooms();

    Room[] getVacantRooms();
}
