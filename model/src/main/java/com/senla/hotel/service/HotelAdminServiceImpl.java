package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.service.interfaces.RoomService;
import com.senla.hotel.utils.Connector;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Singleton
public class HotelAdminServiceImpl implements HotelAdminService {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private RoomHistoryService roomHistoryService;
    @Autowired
    private RoomHistoryDao roomHistoryRepository;
    @Autowired
    private Connector connector;

    @Override
    public void checkIn(final Long residentId, final Long roomId, final LocalDate checkIn, final LocalDate checkOut)
            throws EntityNotFoundException, PersistException, SQLException {
        final Room room = roomService.findById(roomId);
        final Resident resident = residentService.findById(residentId);
        if (room.getStatus() == RoomStatus.VACANT) {
            try {
                connector.getConnection().setAutoCommit(false);
                final RoomHistory history = new RoomHistory(room, resident, checkIn, checkOut, HistoryStatus.CHECKED_IN);
                roomHistoryRepository.create(history);
                roomService.changeRoomStatus(roomId, RoomStatus.OCCUPIED);
                connector.getConnection().commit();
            } catch (final SQLException e) {
                connector.getConnection().rollback();
                throw new PersistException("Transaction is being rolled back");
            } finally {
                connector.getConnection().setAutoCommit(true);
            }
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
            throws EntityNotFoundException, PersistException, SQLException {
        checkIn(resident.getId(), room.getId(), checkIn, checkOut);
    }

    @Override
    public void checkOut(final Long residentId, final LocalDate date) throws SQLException, PersistException {
        try {
            connector.getConnection().setAutoCommit(false);
            final Resident resident = residentService.findById(residentId);
            final RoomHistory history = resident.getHistory();
            history.setStatus(HistoryStatus.CHECKED_OUT);
            history.setCheckOut(date);
            roomHistoryRepository.update(history);
            final Room room = history.getRoom();
            if (room.getStatus() != RoomStatus.OCCUPIED) {
                System.out.printf("The room №%d has no resident.", room.getNumber());
            } else {
                roomService.changeRoomStatus(room.getId(), RoomStatus.VACANT);
            }
            connector.getConnection().commit();
        } catch (final Exception e) {
            connector.getConnection().rollback();
            throw new PersistException("Transaction is being rolled back");
        } finally {
            connector.getConnection().setAutoCommit(true);
        }
    }

    @Override
    public void checkOut(final Resident resident, final LocalDate date) throws SQLException, PersistException {
        checkOut(resident.getId(), date);
    }

    @Override
    public BigDecimal calculateBill(final Long id) throws EntityNotFoundException, PersistException {
        final Resident resident = residentService.findById(id);
        if (resident.getHistory() != null) {
            final BigDecimal total = roomHistoryRepository.calculateBill(resident.getHistory().getId());
            System.out.printf("%s has to pay %.2f BYN for the room №%d%n",
                    resident.toString(),
                    total,
                    resident.getHistory().getRoom().getNumber());
            return total;
        } else {
            System.out.printf("%s is not checked-in.%n",
                    resident.toString());
        }
        return null;
    }

    @Override
    public BigDecimal calculateBill(final Resident resident) throws EntityNotFoundException, PersistException {
        final Long id = resident.getId();
        return calculateBill(id);
    }

    @Override
    public void importHistories() {
        roomHistoryService.importHistories();
    }

    @Override
    public void exportHistories() throws PersistException {
        roomHistoryService.exportHistories();
    }
}
