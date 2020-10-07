package com.senla.hotel.controller;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.CheckInDto;
import com.senla.hotel.dto.CheckOutDto;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Component
@RestController
@RequestMapping("/admin")
public class HotelController {

    private final HotelAdminService hotelAdminService;
    private final RoomHistoryService roomHistoryService;

    public HotelController(HotelAdminService hotelAdminService, RoomHistoryService roomHistoryService) {
        this.hotelAdminService = hotelAdminService;
        this.roomHistoryService = roomHistoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void checkIn(@RequestBody final CheckInDto checkInDto)
        throws EntityNotFoundException, PersistException, SQLException {
        hotelAdminService.checkIn(checkInDto.getResident(), checkInDto.getRoom(), checkInDto.getCheckIn(), checkInDto.getCheckOut());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void checkOut(@RequestBody final CheckOutDto checkOutDto)
        throws EntityNotFoundException, SQLException, PersistException {
        hotelAdminService.checkOut(checkOutDto.getResident(), checkOutDto.getDate());
    }

    @GetMapping(value = "/bill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDto calculateBill(@PathVariable("id") final Long id) throws EntityNotFoundException, PersistException {
        return new PriceDto(hotelAdminService.calculateBill(id));
    }

    @GetMapping(value = "/import")
    @ResponseStatus(HttpStatus.OK)
    public void importHistories() throws PersistException {
        roomHistoryService.importHistories();
    }

    @GetMapping(value = "/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportHistories() throws PersistException {
        roomHistoryService.exportHistories();
    }

    @GetMapping(value = "/deserialize")
    @ResponseStatus(HttpStatus.OK)
    public void importData() {
        hotelAdminService.deserialize();
    }

    @GetMapping(value = "/serialize")
    @ResponseStatus(HttpStatus.OK)
    public void exportData() throws PersistException {
        hotelAdminService.serialize();
    }
}
