package com.senla.hotel;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.utils.Printer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Test {

    public static void main(final String[] args) throws NoSuchEntityException {

        final AttendanceController attendanceController = AttendanceController.getInstance();
        final RoomController roomController = RoomController.getInstance();
        final ResidentController residentController = ResidentController.getInstance();
        final HotelController hotelController = HotelController.getInstance();

        final Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        final Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
        final Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
        final Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");

        attendanceController.createAttendance(ironing);
        attendanceController.createAttendance(wakeup);
        attendanceController.createAttendance(laundry);
        attendanceController.createAttendance(dogWalking);

        System.out.println("Show all attendances");
        Printer.show(attendanceController.showAttendances());

        System.out.println("Show attendances sorted by name");
        Printer.show(attendanceController.showAttendancesSortedByName());

        System.out.println("Show attendances sorted by price");
        Printer.show(attendanceController.showAttendancesSortedByPrice());

        System.out.println();

        final Room room101 =
                new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        final Room room102 =
                new Room(102, Stars.JUNIOR_SUIT, Accommodation.SGL_2_CHD, BigDecimal.valueOf(120), RoomStatus.VACANT);
        final Room room103 = new Room(103, Stars.SUIT, Accommodation.DBL, BigDecimal.valueOf(150), RoomStatus.VACANT);
        final Room room201 =
                new Room(201, Stars.DE_LUX, Accommodation.TRPL, BigDecimal.valueOf(400), RoomStatus.OCCUPIED);
        final Room room202 =
                new Room(202, Stars.FAMILY_ROOM, Accommodation.TRPL_2_CHD, BigDecimal.valueOf(350), RoomStatus.OCCUPIED);
        final Room room203 = new Room(203, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.REPAIR);
        final Room room301 = new Room(301, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.VACANT);
        final Room room302 =
                new Room(302, Stars.DUPLEX, Accommodation.DBL_EXB, BigDecimal.valueOf(320), RoomStatus.REPAIR);
        final Room room303 =
                new Room(303, Stars.HONEYMOON_ROOM, Accommodation.DBL, BigDecimal.valueOf(440), RoomStatus.OCCUPIED);

        roomController.addRoom(room101);
        roomController.addRoom(room102);
        roomController.addRoom(room103);
        roomController.addRoom(room201);
        roomController.addRoom(room202);
        roomController.addRoom(room203);
        roomController.addRoom(room301);
        roomController.addRoom(room302);
        roomController.addRoom(room303);

        System.out.println("Show all rooms");
        Printer.show(roomController.showAllRooms());

        System.out.println("Sort all rooms by price");
        Printer.show(roomController.showAllRoomsSortedByPrice());

        System.out.println("Sort all rooms by accommodation");
        Printer.show(roomController.showAllRoomsSortedByAccommodation());

        System.out.println("Sort all rooms by stars");
        Printer.show(roomController.showAllRoomsSortedByStars());

        Printer.show(roomController.showVacantRooms());
        System.out.println("Show count vacant rooms");
        Printer.show(roomController.countVacantRooms());
        System.out.println("Sort vacant rooms by price");
        Printer.show(roomController.showVacantRoomsSortedByPrice());

        System.out.println("Sort vacant rooms by accommodation");
        Printer.show(roomController.showVacantRoomsSortedByAccommodation());

        System.out.println("Sort vacant rooms by stars");
        Printer.show(roomController.showVacantRoomsSortedByStars());

        System.out.println("Show details room №202");
        Printer.show(roomController.showRoomDetails(202));

        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null);
        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null);
        final Resident stephani = new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", null);
        final Resident carl = new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", null);

        residentController.createResident(mike);
        residentController.createResident(alex);
        residentController.createResident(juliet);
        residentController.createResident(stephani);
        residentController.createResident(carl);

        System.out.println("Show all residents");
        Printer.show(residentController.showResidents());

        System.out.println("Show count residents");
        Printer.show(residentController.showCountResidents());

        System.out.println("Sort by name");
        Printer.show(residentController.showResidentsSortedByName());

        hotelController.checkIn(mike, room101, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5));
        hotelController.checkIn(alex, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 25));
        hotelController.checkIn(alex, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 10));
        hotelController.checkOut(alex, LocalDate.of(2020, 8, 9));

        hotelController.checkIn(juliet, room103, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 14));
        hotelController.checkOut(juliet, LocalDate.of(2020, 12, 2));
        hotelController.checkIn(stephani, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 13));
        hotelController.checkOut(stephani, LocalDate.of(2020, 8, 13));

        hotelController.checkIn(juliet, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 15));

        residentController.addAttendanceToResident(mike, ironing);
        residentController.addAttendanceToResident(mike, wakeup);

        System.out.println("Calculate bill by resident");
        Printer.show(hotelController.calculateBill(mike));
        Printer.show(hotelController.calculateBill(alex));

        System.out.println("Show 3 last residents by room");
        Printer.show(roomController.showLastResidents(room102, 3));

        Printer.show(residentController.showResidentsSortedByCheckOutDate());

        System.out.println("Vacant rooms on date");
        Printer.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));

        roomController.changePrice(203, BigDecimal.valueOf(2.0));
        System.out.println("Vacant rooms on date after updating status room №203");
        Printer.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));
        roomController.changeStatus(301, RoomStatus.REPAIR);

        System.out.println("Vacant rooms on date after updating status room №301");
        Printer.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));
    }
}
