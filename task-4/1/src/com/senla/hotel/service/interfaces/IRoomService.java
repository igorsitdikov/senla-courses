package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

public interface IRoomService {
    void addHistoryToRoom(Long id, RoomHistory history) throws NoSuchEntityException;

    void updateCheckOutHistory(Long id, RoomHistory history, LocalDate checkOut)
        throws NoSuchEntityException;

    Room[] showVacantRoomsOnDate(LocalDate date);

    Room[] sortRooms(Room[] rooms, Comparator<Room> comparator);

    Room findRoomById(Long id) throws NoSuchEntityException;

    Room findRoomByRoomNumber(Integer number) throws NoSuchEntityException;

    Room[] vacantOnDate(LocalDate date);

    Resident[] showLastResidents(Long id, Integer number) throws NoSuchEntityException;

    Resident[] showLastResidents(Room room, Integer number) throws NoSuchEntityException;

    int countVacantRooms();

    void changeRoomPrice(Long id, BigDecimal price);

    void changeRoomPrice(Integer number, BigDecimal price);

    void changeRoomStatus(Long id, RoomStatus status) throws NoSuchEntityException;

    void changeRoomStatus(Integer number, RoomStatus status) throws NoSuchEntityException;

    void addRoom(Room room);

    Room[] showAllRooms();

    Room[] showAllRoomsSortedByPrice();

    Room[] showAllRoomsSortedByAccommodation();

    Room[] showAllRoomsSortedByStars();

    Room[] showVacantRooms();

    Room[] showVacantRoomsSortedByPrice();

    Room[] showVacantRoomsSortedByAccommodation();

    Room[] showVacantRoomsSortedByStars();

    Room showRoomDetails(Integer number) throws NoSuchEntityException;
}
