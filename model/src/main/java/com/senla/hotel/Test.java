//package com.senla.hotel;
//
//import com.senla.di.Di;
//import com.senla.di.DiFramework;
//import com.senla.anntotaion.Autowired;
//import com.senla.hotel.controller.AttendanceController;
//import com.senla.hotel.controller.HotelController;
//import com.senla.hotel.controller.ResidentController;
//import com.senla.hotel.controller.RoomController;
//import com.senla.hotel.entity.Attendance;
//import com.senla.hotel.entity.Resident;
//import com.senla.hotel.entity.Room;
//import com.senla.hotel.enumerated.Accommodation;
//import com.senla.hotel.enumerated.Gender;
//import com.senla.hotel.enumerated.RoomStatus;
//import com.senla.hotel.enumerated.Stars;
//import com.senla.hotel.exceptions.EntityAlreadyExistsException;
//import com.senla.hotel.exceptions.RoomStatusChangingException;
//import com.senla.hotel.repository.AttendanceRepository;
//import com.senla.hotel.repository.ResidentRepository;
//import com.senla.hotel.repository.RoomHistoryRepository;
//import com.senla.hotel.repository.RoomRepository;
//import com.senla.hotel.repository.interfaces.IAttendanceRepository;
//import com.senla.hotel.repository.interfaces.IResidentRepository;
//import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
//import com.senla.hotel.repository.interfaces.IRoomRepository;
//import com.senla.hotel.service.*;
//import com.senla.hotel.service.interfaces.*;
//import com.senla.hotel.utils.PrinterUtils;
//import com.senla.hotel.utils.SerializationUtils;
//import com.senla.hotel.utils.csv.interfaces.ICsvReader;
//import com.senla.hotel.utils.csv.interfaces.ICsvWriter;
//import com.senla.hotel.utils.csv.reader.CsvReader;
//import com.senla.hotel.utils.csv.writer.CsvWriter;
//import com.senla.hotel.utils.settings.PropertyLoader;
//import com.senla.hotel.utils.settings.interfaces.IPropertyLoader;
//import com.senla.di.module.impl.AbstractModule;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public class Test {
//    @Autowired
//    private static IAttendanceController attendanceController;
//    @Autowired
//    private static IAttendanceService attendanceService;
//    @Autowired
//    private static IRoomController roomController;
//    @Autowired
//    private static IResidentController residentController;
//    @Autowired
//    private static IHotelController hotelController;
//
//    public static void main(final String[] args) throws Exception {
//        DiFramework ownDi = Di.getFramework(new DependencyInjectionConfig());
//        ownDi.inject(PropertyLoader.class);
//        ownDi.inject(CsvReader.class);
//        ownDi.inject(CsvWriter.class);
//        ownDi.inject(RoomRepository.class);
//        ownDi.inject(AttendanceRepository.class);
//        ownDi.inject(ResidentRepository.class);
//        ownDi.inject(RoomHistoryRepository.class);
//        ownDi.inject(AttendanceService.class);
//        ownDi.inject(ResidentService.class);
//        ownDi.inject(RoomHistoryService.class);
//        ownDi.inject(HotelAdminService.class);
//        ownDi.inject(RoomService.class);
//        ownDi.inject(AttendanceController.class);
//        ownDi.inject(HotelController.class);
//        ownDi.inject(ResidentController.class);
//        ownDi.inject(RoomController.class);
//
//        ownDi.inject(Test.class);
//
////        Injector.test("com.senla.hotel");
//
//        SerializationUtils.deserialize();
//
//        final Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
//        final Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
//        final Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
//        final Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");
//        try {
//            attendanceController.createAttendance(ironing);
//        } catch (final EntityAlreadyExistsException e) {
//            System.err.println(e.getMessage());
//        }
//        try {
//            attendanceController.createAttendance(wakeup);
//        } catch (final EntityAlreadyExistsException e) {
//            System.err.println(e.getMessage());
//        }
//        try {
//            attendanceController.createAttendance(laundry);
//        } catch (final EntityAlreadyExistsException e) {
//            System.err.println(e.getMessage());
//        }
//        try {
//            attendanceController.createAttendance(dogWalking);
//        } catch (final EntityAlreadyExistsException e) {
//            System.err.println(e.getMessage());
//        }
//        attendanceController.deleteAttendance(4L);
//        PrinterUtils.show("Show all attendances");
//        PrinterUtils.show(attendanceController.showAttendances());
//        PrinterUtils.show("Show attendances sorted by name");
//        PrinterUtils.show(attendanceController.showAttendancesSortedByName());
//        PrinterUtils.show("Show attendances sorted by price");
//        PrinterUtils.show(attendanceController.showAttendancesSortedByPrice());
//        final Room room101 =
//                new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
//        final Room room102 =
//                new Room(102, Stars.JUNIOR_SUIT, Accommodation.SGL_2_CHD, BigDecimal.valueOf(120), RoomStatus.VACANT);
//        final Room room103 = new Room(103, Stars.SUIT, Accommodation.DBL, BigDecimal.valueOf(150), RoomStatus.VACANT);
//        final Room room201 =
//                new Room(201, Stars.DE_LUX, Accommodation.TRPL, BigDecimal.valueOf(400), RoomStatus.OCCUPIED);
//        final Room room202 =
//                new Room(202, Stars.FAMILY_ROOM, Accommodation.TRPL_2_CHD, BigDecimal.valueOf(350), RoomStatus.OCCUPIED);
//        final Room room203 = new Room(203, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.REPAIR);
//        final Room room301 = new Room(301, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.VACANT);
//        final Room room302 =
//                new Room(302, Stars.DUPLEX, Accommodation.DBL_EXB, BigDecimal.valueOf(320), RoomStatus.REPAIR);
//        final Room room303 =
//                new Room(303, Stars.HONEYMOON_ROOM, Accommodation.DBL, BigDecimal.valueOf(440), RoomStatus.OCCUPIED);
//        roomController.addRoom(room101);
//        roomController.addRoom(room102);
//        roomController.addRoom(room103);
//        roomController.addRoom(room201);
//        roomController.addRoom(room202);
//        roomController.addRoom(room203);
//        roomController.addRoom(room301);
//        roomController.addRoom(room302);
//        roomController.addRoom(room303);
//        PrinterUtils.show("Show all rooms");
//        PrinterUtils.show(roomController.showAllRooms());
//        PrinterUtils.show("Sort all rooms by price");
//        PrinterUtils.show(roomController.showAllRoomsSortedByPrice());
//        PrinterUtils.show("Sort all rooms by accommodation");
//        PrinterUtils.show(roomController.showAllRoomsSortedByAccommodation());
//        PrinterUtils.show("Sort all rooms by stars");
//        PrinterUtils.show(roomController.showAllRoomsSortedByStars());
//        PrinterUtils.show(roomController.showVacantRooms());
//        PrinterUtils.show("Show count vacant rooms");
//        PrinterUtils.show(roomController.countVacantRooms());
//        PrinterUtils.show("Sort vacant rooms by price");
//        PrinterUtils.show(roomController.showVacantRoomsSortedByPrice());
//        PrinterUtils.show("Sort vacant rooms by accommodation");
//        PrinterUtils.show(roomController.showVacantRoomsSortedByAccommodation());
//        PrinterUtils.show("Sort vacant rooms by stars");
//        PrinterUtils.show(roomController.showVacantRoomsSortedByStars());
//        PrinterUtils.show("Show details room №202");
//        PrinterUtils.show(roomController.showRoomDetails(202));
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null);
//        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null);
//        final Resident stephani = new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", null);
//        final Resident carl = new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", null);
//        residentController.createResident(mike);
//        residentController.createResident(alex);
//        residentController.createResident(juliet);
//        residentController.createResident(stephani);
//        residentController.createResident(carl);
//        PrinterUtils.show("Show all residents");
//        PrinterUtils.show(residentController.showResidents());
//        PrinterUtils.show("Show count residents");
//        PrinterUtils.show(residentController.showCountResidents());
//        PrinterUtils.show("Sort by name");
//        PrinterUtils.show(residentController.showResidentsSortedByName());
//        hotelController.checkIn(mike, room101, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5));
//        hotelController.checkIn(alex, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 25));
//        hotelController.checkIn(alex, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 10));
//        hotelController.checkOut(alex, LocalDate.of(2020, 8, 9));
//        hotelController.checkIn(juliet, room103, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 14));
//        hotelController.checkOut(juliet, LocalDate.of(2020, 12, 2));
//        hotelController.checkIn(stephani, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 13));
//        hotelController.checkOut(stephani, LocalDate.of(2020, 8, 13));
//        hotelController.checkIn(juliet, room102, LocalDate.of(2020, 8, 6), LocalDate.of(2020, 8, 15));
//        residentController.addAttendanceToResident(mike, ironing);
//        residentController.addAttendanceToResident(juliet, wakeup);
//        PrinterUtils.show("Calculate bill by resident");
//        PrinterUtils.show(hotelController.calculateBill(mike));
//        PrinterUtils.show(hotelController.calculateBill(alex));
//        PrinterUtils.show("Show 3 last residents by room");
//        PrinterUtils.show(roomController.showLastResidents(room102, 3));
//        PrinterUtils.show(residentController.showResidentsSortedByCheckOutDate());
//        PrinterUtils.show("Vacant rooms on date");
//        PrinterUtils.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));
//        roomController.changePrice(203, BigDecimal.valueOf(2.0));
//        PrinterUtils.show("Vacant rooms on date after updating status room №203");
//        PrinterUtils.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));
//        try {
//            roomController.changeStatus(301, RoomStatus.REPAIR);
//        } catch (final RoomStatusChangingException e) {
//            System.err.println(e.getMessage());
//        }
//        PrinterUtils.show("Vacant rooms on date after updating status room №301");
//        PrinterUtils.show(roomController.showVacantRoomsOnDate(LocalDate.of(2020, 8, 13)));
//        roomController.exportRooms();
//        residentController.exportResidents();
//        attendanceController.exportAttendances();
//        hotelController.exportHistories();
//        roomController.importRooms();
//        residentController.importResidents();
//        attendanceController.importAttendances();
//        hotelController.importHistories();
//        SerializationUtils.serialize(
//                attendanceController.showAttendances(),
//                roomController.showAllRooms(),
//                residentController.showResidents(),
//                hotelController.showHistories()
//        );
//    }
//
//    private static class DependencyInjectionConfig extends AbstractModule {
//        @Override
//        public void configure() {
//            createMapping(ICsvReader.class, CsvReader.class);
//            createMapping(ICsvWriter.class, CsvWriter.class);
//            createMapping(IPropertyLoader.class, PropertyLoader.class);
//            createMapping(IAttendanceRepository.class, AttendanceRepository.class);
//            createMapping(IResidentRepository.class, ResidentRepository.class);
//            createMapping(IRoomHistoryRepository.class, RoomHistoryRepository.class);
//            createMapping(IRoomRepository.class, RoomRepository.class);
//            createMapping(IAttendanceService.class, AttendanceService.class);
//            createMapping(IHotelAdminService.class, HotelAdminService.class);
//            createMapping(IResidentService.class, ResidentService.class);
//            createMapping(IRoomHistoryService.class, RoomHistoryService.class);
//            createMapping(IRoomService.class, RoomService.class);
//            createMapping(IAttendanceController.class, AttendanceController.class);
//            createMapping(IHotelController.class, HotelController.class);
//            createMapping(IResidentController.class, ResidentController.class);
//            createMapping(IRoomController.class, RoomController.class);
//        }
//    }
//}
//
