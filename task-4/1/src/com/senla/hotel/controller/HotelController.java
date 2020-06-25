package com.senla.hotel.controller;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.interfaces.IHotelAdminService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HotelController {
    private static HotelController hotelController;
    private static IHotelAdminService hotelAdminService;

    private HotelController() {
        hotelAdminService = HotelAdminService.getInstance();
    }

    public static HotelController getInstance() {
        if (hotelController == null) {
            hotelController = new HotelController();
        }
        return hotelController;
    }

    public void checkIn(Resident resident, Room room, LocalDate checkIn, LocalDate checkOut)
        throws EntityNotFoundException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(Resident resident, LocalDate date) throws EntityNotFoundException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(Resident resident) throws EntityNotFoundException {
        return hotelAdminService.calculateBill(resident);
    }

    public void importHistories() {
        hotelAdminService.importHistories();
    }

    public void exportHistories() {
        hotelAdminService.exportHistories();
    }

}
