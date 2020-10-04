package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public interface HotelAdminService {

    void checkIn(Long residentId, Long roomId, LocalDate checkIn, LocalDate checkOut)
            throws EntityNotFoundException, SQLException, PersistException;

    void checkIn(ResidentDto resident, RoomDto room, LocalDate checkIn, LocalDate checkOut)
            throws EntityNotFoundException, PersistException, SQLException;

    void checkOut(Long residentId, LocalDate date) throws EntityNotFoundException, SQLException, PersistException;

    void checkOut(ResidentDto resident, LocalDate date)
            throws EntityNotFoundException, SQLException, PersistException;

    BigDecimal calculateBill(Long id) throws EntityNotFoundException, PersistException;

    BigDecimal calculateBill(ResidentDto resident) throws EntityNotFoundException, PersistException;

    void serialize() throws PersistException;

    void deserialize();
}
