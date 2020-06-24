package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IHotelAdminService {
    void checkIn(Long residentId, Long roomId, LocalDate checkIn, LocalDate checkOut)
            throws NoSuchEntityException;

    void checkIn(Resident resident, Room room, LocalDate checkIn, LocalDate checkOut)
            throws NoSuchEntityException;

    void checkOut(Long residentId, LocalDate date) throws NoSuchEntityException;

    void checkOut(Resident resident, LocalDate date)
            throws NoSuchEntityException;

    BigDecimal calculateBill(Long id) throws NoSuchEntityException;

    BigDecimal calculateBill(Resident resident) throws NoSuchEntityException;

    void importHistories();

    void exportHistories();
}
