package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class HotelController {
    @Autowired
    private static HotelAdminService hotelAdminService;
    @Autowired
    private static AttendanceService attendanceService;
    @Autowired
    private static RoomService roomService;
    @Autowired
    private static ResidentService residentService;
    @Autowired
    private static RoomHistoryService historyService;

    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
            throws EntityNotFoundException, PersistException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(final Resident resident, final LocalDate date) throws EntityNotFoundException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(final Resident resident) throws EntityNotFoundException, PersistException {
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
