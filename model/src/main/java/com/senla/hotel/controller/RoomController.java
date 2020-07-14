package com.senla.hotel.controller;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.RoomStatusChangingException;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomController {
    private static RoomController roomController;
    private static IRoomService roomService;
    private static final Boolean STATUS_ALLOW =
        Boolean.parseBoolean(PropertyLoader.getInstance().getProperty("change-status"));

    private RoomController() {
        roomService = RoomService.getInstance();
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

    public List<Room> showAllRooms() {
        return roomService.showAllRooms();
    }

    public List<Room> showVacantRooms() {
        return roomService.showVacantRooms();
    }

    public List<Room> showAllRoomsSortedByAccommodation() {
        return roomService.showAllRoomsSortedByAccommodation();
    }

    public List<Room> showAllRoomsSortedByPrice() {
        return roomService.showAllRoomsSortedByPrice();
    }

    public List<Room> showAllRoomsSortedByStars() {
        return roomService.showAllRoomsSortedByStars();
    }

    public List<Room> showVacantRoomsSortedByAccommodation() {
        return roomService.showVacantRoomsSortedByAccommodation();
    }

    public List<Room> showVacantRoomsSortedByPrice() {
        return roomService.showVacantRoomsSortedByPrice();
    }

    public List<Room> showVacantRoomsSortedByStars() {
        return roomService.showVacantRoomsSortedByStars();
    }

    public Room showRoomDetails(final Integer roomNumber) throws EntityNotFoundException {
        return roomService.showRoomDetails(roomNumber);
    }

    public List<Room> showVacantRoomsOnDate(final LocalDate date) {
        return roomService.showVacantRoomsOnDate(date);
    }

    public void changePrice(final Integer roomNumber, final BigDecimal price) throws EntityNotFoundException {
        roomService.changeRoomPrice(roomNumber, price);
    }

    public List<Resident> showLastResidents(final Room room, final Integer amount) throws EntityNotFoundException {
        return roomService.showLastResidents(room, amount);
    }

    public int countVacantRooms() {
        return roomService.countVacantRooms();
    }

    public void changeStatus(final Integer number, final RoomStatus status)
        throws EntityNotFoundException, RoomStatusChangingException {
        if (!STATUS_ALLOW) {
            throw new RoomStatusChangingException("Changing status is forbidden");
        }
        roomService.changeRoomStatus(number, status);
    }

    public void importRooms() {
        roomService.importRooms();
    }

    public void exportRooms() {
        roomService.exportRooms();
    }
}
