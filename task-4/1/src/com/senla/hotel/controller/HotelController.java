package com.senla.hotel.controller;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.interfaces.IHotelAdminService;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class HotelController {
    private static HotelController hotelController;
    private static IHotelAdminService hotelAdminService;

    public HotelController() {
        hotelAdminService = new HotelAdminService();
    }

    public static HotelController getInstance() {
        if (hotelController == null) {
            hotelController = new HotelController();
        }
        return hotelController;
    }

    public void checkIn(Resident resident, Room room, LocalDate checkIn, LocalDate checkOut) throws NoSuchEntityException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(Resident resident, LocalDate date) throws NoSuchEntityException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(Resident resident) throws NoSuchEntityException {
        return hotelAdminService.calculateBill(resident);
    }

}
