package com.senla.hotel.controller;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.exceptions.RoomStatusChangingException;
import com.senla.hotel.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Value("${RoomController.statusAllow:true}")
    private Boolean statusAllow;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoom(@RequestBody final RoomDto room) throws PersistException {
        roomService.addRoom(room);
    }

    @GetMapping(value = "/all/sort/{sortField}")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto> showAllRooms(@PathVariable("sortField") final SortField sortField) throws PersistException {
        return roomService.showAll(sortField);
    }

    @GetMapping(value = "/vacant/sort/{sortField}")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto> showVacantRooms(@PathVariable("sortField") final SortField sortField) throws PersistException {
        return roomService.showVacant(sortField);
    }

    @GetMapping(value = "/{roomNumber}")
    @ResponseStatus(HttpStatus.OK)
    public RoomDto showRoomDetails(@PathVariable final Integer roomNumber) throws EntityNotFoundException, PersistException {
        return roomService.showRoomDetails(roomNumber);
    }

    @GetMapping(value = "/vacant")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto> showVacantRoomsOnDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                               @RequestParam("date") final LocalDate date) throws PersistException {
        return roomService.showVacantRoomsOnDate(date);
    }

    @PatchMapping(value = "/{roomNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void changePrice(@PathVariable final Integer roomNumber, @RequestBody final BigDecimal price) throws EntityNotFoundException, PersistException {
        roomService.changeRoomPrice(roomNumber, price);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResidentDto> showLastResidents(@RequestParam("id") final Long id, @RequestParam("amount") final Integer amount) throws EntityNotFoundException, PersistException {
        return roomService.showLastResidents(id, amount);
    }

    @GetMapping(value = "/amount")
    @ResponseStatus(HttpStatus.OK)
    public Long countVacantRooms() throws PersistException {
        return roomService.countVacantRooms();
    }

    @PatchMapping(value = "/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatus(@PathVariable final Integer number, @RequestBody final RoomStatus status)
            throws EntityNotFoundException, RoomStatusChangingException, PersistException {
        if (!statusAllow) {
            throw new RoomStatusChangingException("Changing status is forbidden");
        }
        roomService.changeRoomStatus(number, status);
    }

    @GetMapping(value = "/import")
    @ResponseStatus(HttpStatus.OK)
    public void importRooms() throws PersistException {
        roomService.importRooms();
    }

    @GetMapping(value = "/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportRooms() throws PersistException {
        roomService.exportRooms();
    }
}
