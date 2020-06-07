package com.senla.hotel.service;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.time.LocalDate;

public class HotelAdminService {
    private RoomService roomService = new RoomService();
    private ResidentService residentService = new ResidentService();
    private AttendanceService attendanceService = new AttendanceService();
    private RoomHistoryService roomHistoryService = new RoomHistoryService();

    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut) {
        if (room.getStatus() == RoomStatus.VACANT) {
            final RoomHistory history = new RoomHistory(room.getId(), resident.getId(), checkIn, checkOut);
            final RoomHistory roomHistoryEntity = roomHistoryService.create(history);
        }
    }

    public void changeRoomStatus(final Long id, final RoomStatus status) throws NoSuchEntityException {
        final Room room = roomService.getRoomById(id);

        if (room == null) {
            throw new NoSuchEntityException(String.format("No room with id %d%n", id));
        }
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is already occupied");
        } else if (room.getStatus() == RoomStatus.REPAIR && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is not available now");
        } else {
            room.setStatus(status);
        }
    }

}
