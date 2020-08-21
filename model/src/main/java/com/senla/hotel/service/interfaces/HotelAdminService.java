package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public interface HotelAdminService {
    void checkIn(Long residentId, Long roomId, LocalDate checkIn, LocalDate checkOut)
            throws EntityNotFoundException, SQLException, PersistException;

    void checkIn(Resident resident, Room room, LocalDate checkIn, LocalDate checkOut)
            throws EntityNotFoundException, PersistException;

    void checkOut(Long residentId, LocalDate date) throws EntityNotFoundException;

    void checkOut(Resident resident, LocalDate date)
            throws EntityNotFoundException;

    BigDecimal calculateBill(Long id) throws EntityNotFoundException, PersistException;

    BigDecimal calculateBill(Resident resident) throws EntityNotFoundException, PersistException;

    void importHistories();

    void exportHistories();
}
