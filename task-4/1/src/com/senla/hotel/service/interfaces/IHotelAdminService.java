package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.time.LocalDate;

public interface IHotelAdminService {
    void checkIn(Long residentId, Long roomId, LocalDate checkIn, LocalDate checkOut)
        throws NoSuchEntityException;

    void checkOut(Long residentId, Long roomId, LocalDate date) throws NoSuchEntityException;

    void changeRoomStatus(Long id, RoomStatus status) throws NoSuchEntityException;

    void calculateBill(Long id) throws NoSuchEntityException;

    void showLastResidents(Long id, Integer number) throws NoSuchEntityException;

    void createAttendance(Attendance attendance);

    void showAttendances();

    void showAttendancesSortedByName();

    void showAttendancesSortedByPrice();

    void addRoom(Room room);

    void showAllRooms();

    void showAllRoomsSortedByPrice();

    void showAllRoomsSortedByAccommodation();

    void showAllRoomsSortedByStars();

    void showVacantRooms();

    void showVacantRoomsSortedByPrice();

    void showVacantRoomsSortedByAccommodation();

    void showVacantRoomsSortedByStars();

    void showRoomDetails(Long id) throws NoSuchEntityException;

    void addResident(Resident resident);

    void showResidents();

    void showResidentsSortedByName();

    void showResidentsSortedByCheckOutDate();

    void showCountVacantRooms();

    void showCountResidents();

    void addAttendanceToResident(Long id, Attendance attendance) throws NoSuchEntityException;
}
