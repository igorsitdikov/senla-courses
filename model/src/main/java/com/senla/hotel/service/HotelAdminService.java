package com.senla.hotel.service;

import com.senla.anntotaion.Autowired;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.service.interfaces.IRoomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HotelAdminService implements IHotelAdminService {
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IResidentService residentService;
    @Autowired
    private IRoomHistoryService roomHistoryService;

    @Override
    public void checkIn(final Long residentId, final Long roomId, final LocalDate checkIn, final LocalDate checkOut)
        throws EntityNotFoundException {
        final Room room = roomService.findById(roomId);
        final Resident resident = residentService.findById(residentId);
        if (room.getStatus() == RoomStatus.VACANT) {
            final RoomHistory history = new RoomHistory(room, resident, checkIn, checkOut);
            final RoomHistory roomHistoryEntity = roomHistoryService.create(history);
            residentService.addHistoryToResident(residentId, roomHistoryEntity);
            roomService.addHistoryToRoom(roomId, roomHistoryEntity);
            roomService.changeRoomStatus(roomId, RoomStatus.OCCUPIED);
            System.out.printf("%s was checked-in in room №%d%n",
                              resident.toString(),
                              room.getNumber());
        } else if (room.getStatus() == RoomStatus.OCCUPIED) {
            System.out.printf("Room №%d is already in used.%n", room.getNumber());
        } else {
            System.out.printf("Room №%d is repaired.%n", room.getNumber());
        }
    }

    @Override
    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
        throws EntityNotFoundException {
        checkIn(resident.getId(), room.getId(), checkIn, checkOut);
    }

    @Override
    public void checkOut(final Long residentId, final LocalDate date)
        throws EntityNotFoundException {
        final Resident resident = residentService.findById(residentId);
        final RoomHistory history = resident.getHistory();
        final Room room = history.getRoom();
        if (room.getStatus() != RoomStatus.OCCUPIED) {
            System.out.printf("The room №%d has no resident.", room.getNumber());
        } else {
            roomService.changeRoomStatus(room.getId(), RoomStatus.VACANT);
            roomService.updateCheckOutHistory(room.getId(), history, date);
            resident.setHistory(null);
        }
    }

    @Override
    public void checkOut(final Resident resident, final LocalDate date)
        throws EntityNotFoundException {
        checkOut(resident.getId(), date);
    }

    @Override
    public BigDecimal calculateBill(final Long id) throws EntityNotFoundException {
        final Resident resident = residentService.findById(id);
        if (resident.getHistory() != null) {
            final long days = ChronoUnit.DAYS.between(resident.getHistory().getCheckIn(),
                                                      resident.getHistory().getCheckOut());
            final Room room = resident.getHistory().getRoom();
            BigDecimal totalAttendances = BigDecimal.valueOf(0);
            for (int i = 0; i < resident.getHistory().getAttendances().size(); i++) {
                totalAttendances = totalAttendances.add(resident.getHistory().getAttendances().get(i).getPrice());
            }
            final BigDecimal total = room.getPrice()
                .multiply(new BigDecimal(days))
                .add(totalAttendances.multiply(new BigDecimal(days)));
            System.out.printf("%s has to pay %.2f BYN for the room №%d%n",
                              resident.toString(),
                              total,
                              room.getNumber());
            return total;
        } else {
            System.out.printf("%s is not checked-in.%n",
                              resident.toString());
        }
        return null;
    }

    @Override
    public BigDecimal calculateBill(final Resident resident) throws EntityNotFoundException {
        final Long id = resident.getId();
        return calculateBill(id);
    }

    @Override
    public void importHistories() {
        roomHistoryService.importHistories();
    }

    @Override
    public void exportHistories() {
        roomHistoryService.exportHistories();
    }
}
