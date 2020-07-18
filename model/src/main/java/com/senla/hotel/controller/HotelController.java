package com.senla.hotel.controller;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.SerializationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class HotelController {
    @Autowired
    private static IHotelAdminService hotelAdminService;
    @Autowired
    private static IAttendanceService attendanceService;
    @Autowired
    private static IRoomService roomService;
    @Autowired
    private static IResidentService residentService;
    @Autowired
    private static IRoomHistoryService historyService;
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
