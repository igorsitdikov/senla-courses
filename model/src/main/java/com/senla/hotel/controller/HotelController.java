package com.senla.hotel.controller;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.service.interfaces.RoomService;
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

    public void checkIn(final ResidentDTO resident, final RoomDTO room, final LocalDate checkIn, final LocalDate checkOut)
            throws EntityNotFoundException, PersistException, SQLException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(final ResidentDTO resident, final LocalDate date) throws EntityNotFoundException, SQLException, PersistException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(final ResidentDTO resident) throws EntityNotFoundException, PersistException {
        return hotelAdminService.calculateBill(resident);
    }

    public void importHistories() throws PersistException {
        roomHistoryService.importHistories();
    }

    public void exportHistories() throws PersistException {
        roomHistoryService.exportHistories();
    }

    public List<RoomHistoryDTO> showHistories() throws PersistException, EntityIsEmptyException {
        return historyService.showHistories();
    }

    public void importData() {
        serializationUtils.deserialize();
    }

    public void exportData() throws PersistException, EntityIsEmptyException {
        final List<AttendanceDTO> attendances = attendanceService.showAttendances(SortField.DEFAULT);
        final List<RoomDTO> rooms = roomService.showAll(SortField.DEFAULT);
        final List<ResidentDTO> residents = residentService.showResidents(SortField.DEFAULT);
        final List<RoomHistoryDTO> roomHistories = showHistories();
//        serializationUtils.serialize(attendances, rooms, residents, roomHistories);
    }
}
