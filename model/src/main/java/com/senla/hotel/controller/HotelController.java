package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.*;
import com.senla.hotel.utils.SerializationUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class HotelController {
    @Autowired
    private static HotelAdminService hotelAdminService;
    @Autowired
    private static AttendanceService attendanceService;
    @Autowired
    private static RoomHistoryService roomHistoryService;
    @Autowired
    private static RoomService roomService;
    @Autowired
    private static ResidentService residentService;
    @Autowired
    private static RoomHistoryService historyService;
    @Autowired
    private static SerializationUtils serializationUtils;

    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
            throws EntityNotFoundException, PersistException, SQLException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(final Resident resident, final LocalDate date) throws EntityNotFoundException, SQLException, PersistException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(final Resident resident) throws EntityNotFoundException, PersistException {
        return hotelAdminService.calculateBill(resident);
    }

    public void importHistories() throws PersistException {
        roomHistoryService.importHistories();
    }

    public void exportHistories() throws PersistException {
        roomHistoryService.exportHistories();
    }

    public List<RoomHistory> showHistories() throws PersistException {
        return historyService.showHistories();
    }

    public void importData() {
        serializationUtils.deserialize();
    }

    public void exportData() throws PersistException {
        final List<Attendance> attendances = attendanceService.showAttendances(SortField.DEFAULT);
        final List<Room> rooms = roomService.showAll(SortField.DEFAULT);
        final List<Resident> residents = residentService.showResidents(SortField.DEFAULT);
        final List<RoomHistory> roomHistories = showHistories();
        serializationUtils.serialize(attendances, rooms, residents, roomHistories);
    }
}
