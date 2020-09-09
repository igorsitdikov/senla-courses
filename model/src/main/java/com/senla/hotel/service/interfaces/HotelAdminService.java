package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.dto.RoomDTO;
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

    void checkIn(ResidentDTO resident, RoomDTO room, LocalDate checkIn, LocalDate checkOut)
            throws EntityNotFoundException, PersistException, SQLException;

    void checkOut(Long residentId, LocalDate date) throws EntityNotFoundException, SQLException, PersistException;

    void checkOut(ResidentDTO resident, LocalDate date)
            throws EntityNotFoundException, SQLException, PersistException;

    BigDecimal calculateBill(Long id) throws EntityNotFoundException, PersistException;

    BigDecimal calculateBill(ResidentDTO resident) throws EntityNotFoundException, PersistException;
}
