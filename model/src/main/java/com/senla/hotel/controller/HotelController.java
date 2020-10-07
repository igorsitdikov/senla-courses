package com.senla.hotel.controller;

import com.senla.hotel.dto.CheckInDto;
import com.senla.hotel.dto.CheckOutDto;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLException;

@Component
@RestController
@RequestMapping("/admin")
public class HotelController {

    @Autowired
    private HotelAdminService hotelAdminService;
    @Autowired
    private RoomHistoryService roomHistoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void checkIn(final CheckInDto checkInDto)
        throws EntityNotFoundException, PersistException, SQLException {
        hotelAdminService.checkIn(checkInDto.getResident(), checkInDto.getRoom(), checkInDto.getCheckIn(), checkInDto.getCheckOut());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void checkOut(final CheckOutDto checkOutDto)
        throws EntityNotFoundException, SQLException, PersistException {
        hotelAdminService.checkOut(checkOutDto.getResident(), checkOutDto.getDate());
    }

    @GetMapping(value = "/bill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateBill(@PathVariable("id") final Long id) throws EntityNotFoundException, PersistException {
        return hotelAdminService.calculateBill(id);
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
