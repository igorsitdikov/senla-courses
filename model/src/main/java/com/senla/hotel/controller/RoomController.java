package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Type;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
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

    public void addRoom(final RoomDTO room) throws PersistException {
        roomService.addRoom(room);
    }

    public List<RoomDTO> showAllRooms(final SortField sortField) throws PersistException {
        return roomService.showAll(sortField);
    }

    public List<RoomDTO> showVacantRooms(final SortField sortField) throws PersistException {
        return roomService.showVacant(sortField);
    }

    public RoomDTO showRoomDetails(final Integer roomNumber) throws EntityNotFoundException, PersistException {
        return roomService.showRoomDetails(roomNumber);
    }

    public List<RoomDTO> showVacantRoomsOnDate(final LocalDate date) throws PersistException {
        return roomService.showVacantRoomsOnDate(date);
    }

    public void changePrice(final Integer roomNumber, final BigDecimal price) throws EntityNotFoundException, PersistException {
        roomService.changeRoomPrice(roomNumber, price);
    }

    public List<ResidentDTO> showLastResidents(final RoomDTO room, final Integer amount) throws EntityNotFoundException, PersistException {
        return roomService.showLastResidents(room, amount);
    }

    public int countVacantRooms() throws PersistException {
        return roomService.countVacantRooms();
    }

    public void changeStatus(final Integer number, final RoomStatus status)
            throws EntityNotFoundException, RoomStatusChangingException, PersistException {
        if (!statusAllow) {
            throw new RoomStatusChangingException("Changing status is forbidden");
        }
        roomService.changeRoomStatus(number, status);
    }

    public void importRooms() throws PersistException {
        roomService.importRooms();
    }

    public void exportRooms() throws PersistException {
        roomService.exportRooms();
    }
}
