package com.senla.hotel;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.Accommodation;
import com.senla.hotel.entity.type.Gender;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.entity.type.Stars;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;

import java.math.BigDecimal;

public class Test {

    public static void main(final String[] args) {
        final IAttendanceService attendanceService = new AttendanceService();

        final Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        final Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
        final Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
        final Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");

        attendanceService.addAttendance(ironing);
        attendanceService.addAttendance(wakeup);
        attendanceService.addAttendance(laundry);
        attendanceService.addAttendance(dogWalking);

        attendanceService.showAllAttendances();
        System.out.println();

        attendanceService.sortAttendances(attendanceService.getAttendances(), new AttendanceNameComparator());

        attendanceService.showAllAttendances();
        System.out.println();

        attendanceService.sortAttendances(attendanceService.getAttendances(), new AttendancePriceComparator());

        attendanceService.showAllAttendances();
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

        final IRoomService roomService = new RoomService();

        roomService.add(room101);
        roomService.add(room102);
        roomService.add(room103);
        roomService.add(room201);
        roomService.add(room202);
        roomService.add(room203);
        roomService.add(room301);
        roomService.add(room302);
        roomService.add(room303);

        System.out.println("Show all rooms");
        roomService.showRooms(roomService.getAllRooms());
        System.out.println("Sort all rooms by price");
        roomService.showRooms(roomService.sortRooms(roomService.getAllRooms(), new RoomPriceComparator()));
        System.out.println("Sort all rooms by accommodation");

        roomService.showRooms(roomService.sortRooms(roomService.getAllRooms(), new RoomAccommodationComparator()));
        System.out.println("Sort all rooms by stars");

        roomService.showRooms(roomService.sortRooms(roomService.getAllRooms(), new RoomStarsComparator()));
        System.out.println("Show empty rooms");
        roomService.showRooms(roomService.getVacantRooms());

        System.out.println("Sort vacant rooms by price");

        roomService.showRooms(roomService.sortRooms(roomService.getVacantRooms(), new RoomPriceComparator()));
        System.out.println("Sort vacant rooms by accommodation");

        roomService.showRooms(roomService.sortRooms(roomService.getVacantRooms(), new RoomAccommodationComparator()));
        System.out.println("Sort vacant rooms by stars");

        roomService.showRooms(roomService.sortRooms(roomService.getVacantRooms(), new RoomStarsComparator()));

        System.out.println("Show details room with id = 5");
        try {
            roomService.showDetails(5L);
        } catch (NoSuchEntityException e) {
            System.out.println(e.getMessage());
        }

        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", new RoomHistory());
        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", new RoomHistory());
        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", new RoomHistory());
        final Resident stephani = new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", new RoomHistory());
        final Resident carl = new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", new RoomHistory());

        final ResidentService residentService = new ResidentService();
        residentService.add(mike);
        residentService.add(alex);
        residentService.add(juliet);
        residentService.add(stephani);
        residentService.add(carl);
        System.out.println("Show all residents");
        residentService.showResidents(residentService.getResidents());

        System.out.println("Sort by name");
        residentService
            .showResidents(
                residentService.sortResidents(residentService.getResidents(), new ResidentFullNameComparator()));

    }
}
