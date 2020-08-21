package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Type;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.RoomStatusChangingException;
import com.senla.hotel.service.interfaces.RoomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class RoomController {
    @Autowired
    private static RoomService roomService;
    @PropertyLoad(type = Type.BOOLEAN)
    private Boolean statusAllow;

    public void addRoom(final Room room) {
        roomService.addRoom(room);
    }

    public List<Room> showAllRooms(final SortField sortField) {
        return roomService.showAll(sortField);
    }

    public List<Room> showVacantRooms(final SortField sortField) {
        return roomService.showVacant(sortField);
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
        if (!statusAllow) {
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
