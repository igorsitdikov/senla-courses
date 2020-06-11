package com.senla.hotel;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.type.Accommodation;
import com.senla.hotel.entity.type.Gender;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.entity.type.Stars;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IHotelAdminService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Test {

    public static void main(final String[] args) throws NoSuchEntityException {

        final IHotelAdminService hotelAdminService = new HotelAdminService();

        final Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        final Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
        final Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
        final Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");

        hotelAdminService.createAttendance(ironing);
        hotelAdminService.createAttendance(wakeup);
        hotelAdminService.createAttendance(laundry);
        hotelAdminService.createAttendance(dogWalking);

        System.out.println("Show all attendances");
        hotelAdminService.showAttendances();

        System.out.println("Show attendances sorted by name");
        hotelAdminService.showAttendancesSortedByName();

        System.out.println("Show attendances sorted by price");
        hotelAdminService.showAttendancesSortedByPrice();

        System.out.println();

        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        final Room room102 =
            new Room(102, Stars.JUNIOR_SUIT, Accommodation.SGL_2_CHD, BigDecimal.valueOf(120), RoomStatus.VACANT);
        final Room room103 = new Room(103, Stars.SUIT, Accommodation.DBL, BigDecimal.valueOf(150), RoomStatus.VACANT);
        final Room room201 =
            new Room(201, Stars.DE_LUX, Accommodation.TRPL, BigDecimal.valueOf(400), RoomStatus.OCCUPIED);
        final Room room202 =
            new Room(202, Stars.FAMILY_ROOM, Accommodation.TRPL_2_CHLD, BigDecimal.valueOf(350), RoomStatus.OCCUPIED);
        final Room room203 = new Room(203, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.REPAIR);
        final Room room301 = new Room(301, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.VACANT);
        final Room room302 =
            new Room(302, Stars.DUPLEX, Accommodation.DBL_EXB, BigDecimal.valueOf(320), RoomStatus.REPAIR);
        final Room room303 =
            new Room(303, Stars.HONEYMOON_ROOM, Accommodation.DBL, BigDecimal.valueOf(440), RoomStatus.OCCUPIED);

        hotelAdminService.addRoom(room101);
        hotelAdminService.addRoom(room102);
        hotelAdminService.addRoom(room103);
        hotelAdminService.addRoom(room201);
        hotelAdminService.addRoom(room202);
        hotelAdminService.addRoom(room203);
        hotelAdminService.addRoom(room301);
        hotelAdminService.addRoom(room302);
        hotelAdminService.addRoom(room303);

        System.out.println("Show all rooms");
        hotelAdminService.showAllRooms();

        System.out.println("Sort all rooms by price");
        hotelAdminService.showAllRoomsSortedByPrice();

        System.out.println("Sort all rooms by accommodation");
        hotelAdminService.showAllRoomsSortedByAccommodation();

        System.out.println("Sort all rooms by stars");
        hotelAdminService.showAllRoomsSortedByStars();

        hotelAdminService.showVacantRooms();
        System.out.println("Show count vacant rooms");
        hotelAdminService.showCountVacantRooms();
        System.out.println("Sort vacant rooms by price");
        hotelAdminService.showVacantRoomsSortedByPrice();

        System.out.println("Sort vacant rooms by accommodation");
        hotelAdminService.showVacantRoomsSortedByAccommodation();

        System.out.println("Sort vacant rooms by stars");
        hotelAdminService.showVacantRoomsSortedByStars();

        System.out.println("Show details room with id = 5");
        try {
            hotelAdminService.showRoomDetails(5L);
        } catch (NoSuchEntityException e) {
            System.out.println(e.getMessage());
        }

        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null);
        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null);
        final Resident stephani = new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", null);
        final Resident carl = new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", null);

        hotelAdminService.addResident(mike);
        hotelAdminService.addResident(alex);
        hotelAdminService.addResident(juliet);
        hotelAdminService.addResident(stephani);
        hotelAdminService.addResident(carl);

        System.out.println("Show all residents");
        hotelAdminService.showResidents();

        System.out.println("Show count residents");
        hotelAdminService.showCountResidents();

        System.out.println("Sort by name");
        hotelAdminService.showResidentsSortedByName();

        hotelAdminService.checkIn(1L, 1L, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5));
        hotelAdminService.checkIn(2L, 2L, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 25));
        hotelAdminService.checkIn(2L, 2L, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 10));
        hotelAdminService.checkOut(2L, 2L);

        hotelAdminService.checkIn(3L, 3L, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 14));
        hotelAdminService.checkOut(3L, 2L);
        hotelAdminService.checkIn(4L, 2L, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 13));
//        hotelAdminService.checkOut(4L, 2L);

        hotelAdminService.checkIn(3L, 2L, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 12));
//        hotelAdminService.checkOut(3L, 2L);

        hotelAdminService.addAttendanceToResident(1L, ironing);
        hotelAdminService.addAttendanceToResident(1L, wakeup);

        hotelAdminService.calculateBill(1L);
        hotelAdminService.calculateBill(2L);
        hotelAdminService.showLastResidents(2L, 3);

        hotelAdminService.showResidentsSortedByCheckOutDate();

        System.out.println("vacant on date");
        final RoomService roomService = new RoomService();
        roomService.vacantOnDate(LocalDate.of(2020, 8, 13));
    }
}
