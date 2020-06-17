package com.senla.hotel.controller;

import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IRoomService;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class RoomController {
    private static RoomController roomController;
    private static IRoomService roomService;

    public RoomController() {
        roomService = new RoomService();
    }

    public static RoomController getInstance() {
        if (roomController == null) {
            roomController = new RoomController();
        }
        return roomController;
    }

    public void addRoom(final Room room) {
        roomService.addRoom(room);
    }

    public Room[] showAllRooms() {
        return roomService.showAllRooms();
    }

    public Room[] showVacantRooms() {
        return roomService.showVacantRooms();
    }

    public Room[] showAllRoomsSortedByAccommodation() {
        return roomService.showAllRoomsSortedByAccommodation();
    }

    public Room[] showAllRoomsSortedByPrice() {
        return roomService.showAllRoomsSortedByPrice();
    }

    public Room[] showAllRoomsSortedByStars() {
        return roomService.showAllRoomsSortedByStars();
    }

    public Room[] showVacantRoomsSortedByAccommodation() {
        return roomService.showVacantRoomsSortedByAccommodation();
    }

    public Room[] showVacantRoomsSortedByPrice() {
        return roomService.showVacantRoomsSortedByPrice();
    }

    public Room[] showVacantRoomsSortedByStars() {
        return roomService.showVacantRoomsSortedByStars();
    }

    public Room showRoomDetails(final Integer roomNumber) throws NoSuchEntityException {
        return roomService.showRoomDetails(roomNumber);
    }

    public Room[] showVacantRoomsOnDate(final LocalDate date) {
        return roomService.showVacantRoomsOnDate(date);
    }

    public void changePrice(final Integer roomNumber, final BigDecimal price) {
        roomService.changeRoomPrice(roomNumber, price);
    }

    public int countVacantRooms() {
        return roomService.countVacantRooms();
    }
}
