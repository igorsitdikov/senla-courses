package com.senla.hotel.controller;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.service.interfaces.RoomService;
import com.senla.hotel.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
public class HotelController {

    private final HotelAdminService hotelAdminService;
    private final AttendanceService attendanceService;
    private final RoomHistoryService roomHistoryService;
    private final RoomService roomService;
    private final ResidentService residentService;
    private final RoomHistoryService historyService;
    private final SerializationUtils serializationUtils;

    public HotelController(final HotelAdminService hotelAdminService,
                           final AttendanceService attendanceService,
                           final RoomHistoryService roomHistoryService,
                           final RoomService roomService,
                           final ResidentService residentService,
                           final RoomHistoryService historyService,
                           final SerializationUtils serializationUtils) {
        this.hotelAdminService = hotelAdminService;
        this.attendanceService = attendanceService;
        this.roomHistoryService = roomHistoryService;
        this.roomService = roomService;
        this.residentService = residentService;
        this.historyService = historyService;
        this.serializationUtils = serializationUtils;
    }

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
