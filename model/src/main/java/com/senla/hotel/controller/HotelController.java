package com.senla.hotel.controller;

import com.senla.annotation.Autowired;
import com.senla.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.interfaces.*;
import com.senla.hotel.utils.SerializationUtils;

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
    @Autowired
    private SerializationUtils serializationUtils;

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

    public void importData() {
        serializationUtils.deserialize();
    }

    public void exportData() {
        final List<Attendance> attendances = attendanceService.showAttendances();
        final List<Room> rooms = roomService.showAllRooms();
        final List<Resident> residents = residentService.showResidents();
        final List<RoomHistory> roomHistories = showHistories();
        serializationUtils.serialize(attendances, rooms, residents, roomHistories);
    }
}
