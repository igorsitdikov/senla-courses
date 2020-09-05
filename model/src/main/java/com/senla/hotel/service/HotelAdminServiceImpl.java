package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.utils.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@Singleton
public class HotelAdminServiceImpl implements HotelAdminService {

    private static final Logger logger = LogManager.getLogger(HotelAdminServiceImpl.class);

    @Autowired
    private RoomDao roomRepository;
    @Autowired
    private ResidentDao residentDao;
    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Autowired
    private Connector connector;

    @Override
    public void checkIn(final Long residentId, final Long roomId, final LocalDate checkIn, final LocalDate checkOut)
            throws PersistException, SQLException {
        final Room room = roomRepository.findById(roomId);
        final Resident resident = residentDao.findById(residentId);
        if (room.getStatus() == RoomStatus.VACANT) {
            try {
                connector.getConnection().setAutoCommit(false);
                final RoomHistory history = new RoomHistory(room, resident, checkIn, checkOut, HistoryStatus.CHECKED_IN);
                roomHistoryDao.create(history);
                room.setStatus(RoomStatus.OCCUPIED);
                roomRepository.update(room);
                connector.getConnection().commit();
            } catch (final SQLException e) {
                connector.getConnection().rollback();
                throw new PersistException("Transaction is being rolled back");
            } finally {
                connector.getConnection().setAutoCommit(true);
            }
            logger.info("{} was checked-in in room №{}",
                        resident.toString(),
                        room.getNumber());
        } else if (room.getStatus() == RoomStatus.OCCUPIED) {
            logger.info("Room №{} is already in used.", room.getNumber());
        } else {
            logger.info("Room №{} is repaired.", room.getNumber());
        }
    }

    @Override
    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
            throws PersistException, SQLException {
        checkIn(resident.getId(), room.getId(), checkIn, checkOut);
    }

    @Override
    public void checkOut(final Long residentId, final LocalDate date) throws SQLException, PersistException {
        try {
            connector.getConnection().setAutoCommit(false);
            final Resident resident = residentDao.findById(residentId);
            final RoomHistory history = resident.getHistory().iterator().next();
            history.setStatus(HistoryStatus.CHECKED_OUT);
            history.setCheckOut(date);
            roomHistoryDao.update(history);
            final Room room = history.getRoom();
            if (room.getStatus() != RoomStatus.OCCUPIED) {
                logger.warn("The room №{} has no resident.", room.getNumber());
            } else {
                room.setStatus(RoomStatus.VACANT);
                roomRepository.update(room);
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
    public BigDecimal calculateBill(final Long id) throws PersistException {
        final Resident resident = residentDao.findById(id);
        if (resident.getHistory() != null) {
            final BigDecimal total = roomHistoryDao.calculateBill(resident.getHistory().iterator().next().getId());
            logger.info("{} has to pay {} BYN for the room №{}",
                        resident.toString(),
                        total,
                        resident.getHistory().iterator().next().getRoom().getNumber());
            return total;
        } else {
            logger.warn("{} is not checked-in.",
                        resident.toString());
        }
        return null;
    }

    @Override
    public BigDecimal calculateBill(final Resident resident) throws PersistException {
        final Long id = resident.getId();
        return calculateBill(id);
    }
}
