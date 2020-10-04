package com.senla.hotel.controller;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
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

    @Autowired
    private HotelAdminService hotelAdminService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private RoomHistoryService roomHistoryService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private RoomHistoryService historyService;

    public void checkIn(final ResidentDto resident,
                        final RoomDto room,
                        final LocalDate checkIn,
                        final LocalDate checkOut)
        throws EntityNotFoundException, PersistException, SQLException {
        hotelAdminService.checkIn(resident, room, checkIn, checkOut);
    }

    public void checkOut(final ResidentDto resident, final LocalDate date)
        throws EntityNotFoundException, SQLException, PersistException {
        hotelAdminService.checkOut(resident, date);
    }

    public BigDecimal calculateBill(final ResidentDto resident) throws EntityNotFoundException, PersistException {
        return hotelAdminService.calculateBill(resident);
    }

    public void importHistories() throws PersistException {
        roomHistoryService.importHistories();
    }

    public void exportHistories() throws PersistException {
        roomHistoryService.exportHistories();
    }

    public List<RoomHistoryDto> showHistories() throws PersistException {
        return historyService.showHistories();
    }

    public void importData() {
        hotelAdminService.deserialize();
    }

    public void exportData() throws PersistException {
        hotelAdminService.serialize();
    }
}
