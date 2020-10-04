package com.senla.hotel.controller;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.exceptions.RoomStatusChangingException;
import com.senla.hotel.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Value("${RoomController.statusAllow:true}")
    private Boolean statusAllow;

    public void addRoom(final RoomDto room) throws PersistException {
        roomService.addRoom(room);
    }

    public List<RoomDto> showAllRooms(final SortField sortField) throws PersistException {
        return roomService.showAll(sortField);
    }

    public List<RoomDto> showVacantRooms(final SortField sortField) throws PersistException {
        return roomService.showVacant(sortField);
    }

    public RoomDto showRoomDetails(final Integer roomNumber) throws EntityNotFoundException, PersistException {
        return roomService.showRoomDetails(roomNumber);
    }

    public List<RoomDto> showVacantRoomsOnDate(final LocalDate date) throws PersistException {
        return roomService.showVacantRoomsOnDate(date);
    }

    public void changePrice(final Integer roomNumber, final BigDecimal price) throws EntityNotFoundException, PersistException {
        roomService.changeRoomPrice(roomNumber, price);
    }

    public List<ResidentDto> showLastResidents(final RoomDto room, final Integer amount) throws EntityNotFoundException, PersistException {
        return roomService.showLastResidents(room, amount);
    }

    public Long countVacantRooms() throws PersistException {
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
