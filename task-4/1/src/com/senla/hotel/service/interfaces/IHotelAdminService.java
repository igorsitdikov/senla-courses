package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
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

    void changeAttendancePrice(Long id, BigDecimal price);

    void changeAttendancePrice(String name, BigDecimal price);

    void calculateBill(Long id) throws NoSuchEntityException;

    void calculateBill(Resident resident) throws NoSuchEntityException;

    void showLastResidents(Long id, Integer number) throws NoSuchEntityException;

    void showLastResidents(Room room, Integer number) throws NoSuchEntityException;

    void createAttendance(Attendance attendance);

    void showAttendances();

    void showAttendancesSortedByName();

    void showAttendancesSortedByPrice();

    void addResident(Resident resident);

    void showResidents();

    void showResidentsSortedByName();

    void showResidentsSortedByCheckOutDate();

    void showCountVacantRooms();

    void showCountResidents();

    void addAttendanceToResident(Long id, Long attendanceId) throws NoSuchEntityException;

    void addAttendanceToResident(Resident resident, Attendance attendance)
        throws NoSuchEntityException;
}
