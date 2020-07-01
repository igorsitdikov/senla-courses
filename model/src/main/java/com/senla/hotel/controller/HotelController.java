package com.senla.hotel.controller;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.RoomHistoryService;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.service.interfaces.IRoomHistoryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HotelController {
    private static HotelController hotelController;
    private static IHotelAdminService hotelAdminService;
    private static IRoomHistoryService historyService;

    private HotelController() {
        hotelAdminService = HotelAdminService.getInstance();
        historyService = RoomHistoryService.getInstance();
    }

    public static HotelController getInstance() {
        if (hotelController == null) {
            hotelController = new HotelController();
        }
        return hotelController;
    }

    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
        throws EntityNotFoundException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(final Resident resident, final LocalDate date) throws EntityNotFoundException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(final Resident resident) throws EntityNotFoundException {
        return hotelAdminService.calculateBill(resident);
    }

    public void importHistories() {
        hotelAdminService.importHistories();
    }

    public void exportHistories() {
        hotelAdminService.exportHistories();
    }

    public List<RoomHistory> showHistories() {
        return historyService.showHistories();
    }

}
