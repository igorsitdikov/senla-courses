package com.senla.hotel.ui.menu;

import com.senla.annotation.Autowired;
import com.senla.annotation.Singleton;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.ui.enumerated.AttendanceMenuImpl;
import com.senla.hotel.ui.enumerated.HotelAdminMenuImpl;
import com.senla.hotel.ui.enumerated.MainMenuImpl;
import com.senla.hotel.ui.enumerated.ResidentMenuImpl;
import com.senla.hotel.ui.enumerated.RoomMenuImpl;
import com.senla.hotel.ui.enumerated.ShowAttendancesMenuImpl;
import com.senla.hotel.ui.enumerated.ShowResidentsMenuImpl;
import com.senla.hotel.ui.enumerated.ShowRoomsMenuImpl;
import com.senla.hotel.ui.exceptions.ListEntitiesIsEmptyException;
import com.senla.hotel.ui.utils.EnumConverter;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToStatus;

@Singleton
public class Builder {
    private static final String MAIN_MENU = "Main menu";
    private static final String HOTEL_ADMIN_MENU = "Hotel admin menu";
    private static final String ROOM_MENU = "Room menu";
    private static final String RESIDENT_MENU = "Resident menu";
    private static final String ATTENDANCE_MENU = "Attendance menu";
    private final Menu rootMenu = new Menu(MAIN_MENU);
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private AttendanceController attendanceController;
    @Autowired
    private ResidentController residentController;
    @Autowired
    private HotelController hotelController;
    @Autowired
    private RoomController roomController;

    public Menu buildMenu() {
        final Menu hotelAdminMenu = new Menu(HOTEL_ADMIN_MENU);
        final Menu roomMenu = new Menu(ROOM_MENU);
        final Menu residentMenu = new Menu(RESIDENT_MENU);
        final Menu attendanceMenu = new Menu(ATTENDANCE_MENU);

        this.addHotelAdminSubMenu(hotelAdminMenu, rootMenu);
        this.addRoomSubMenu(roomMenu, rootMenu);
        this.addResidentSubMenu(residentMenu, rootMenu);
        this.addAttendanceSubMenu(attendanceMenu, rootMenu);

        rootMenu.addMenuItem(new MenuItem(MainMenuImpl.HOTEL_ADMIN, hotelAdminMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenuImpl.ROOM, roomMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenuImpl.RESIDENT, residentMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenuImpl.ATTENDANCE, attendanceMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenuImpl.EXIT, null));

        return rootMenu;
    }

    private void addAttendanceSubMenu(final Menu menu, final Menu previous) {
        final Menu showerMenu = new Menu(AttendanceMenuImpl.SHOW_ATTENDANCES.getTitle());
        this.addPrinterAttendances(showerMenu, menu);
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.SHOW_ATTENDANCES, showerMenu));
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.ADD_ATTENDANCE, menu, () -> {
            try {
                final String name = InputDataReader.getStringInput(scanner);
                final BigDecimal dailyPrice =
                    BigDecimal
                        .valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
                attendanceController.createAttendance(new Attendance(dailyPrice, name));
            } catch (final Exception e) {
                System.err.println(String.format("Failed to add a Attendance! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.CHANGE_ATTENDANCE_PRICE, menu, () -> {
            try {
                final String name = InputDataReader.getStringInput(scanner);
                final BigDecimal dailyPrice =
                    BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
                attendanceController.changePrice(name, dailyPrice);
            } catch (final Exception e) {
                System.err.println(String.format("Failed to change price a Attendance! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterAttendances(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.ALL_ATTENDANCES, menu, () -> {
            final List<Attendance> attendances = attendanceController.showAttendances();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.SORT_ATTENDANCES_BY_NAME, menu, () -> {
            final List<Attendance> attendances = attendanceController.showAttendancesSortedByName();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.SORT_ATTENDANCES_BY_PRICE, menu, () -> {
            final List<Attendance> attendances =
                attendanceController.showAttendancesSortedByPrice();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addResidentSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(ResidentMenuImpl.SHOW_RESIDENTS.getTitle());
        this.addPrinterResidents(printerMenu, menu);
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.SHOW_RESIDENTS, printerMenu));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.ADD_RESIDENT, menu, () -> {
            try {
                final String firstName = InputDataReader.getStringInput(scanner);
                final String lastName = InputDataReader.getStringInput(scanner);
                final String phone = InputDataReader.getStringInput(scanner);
                final Boolean vip = InputDataReader.getIntegerInput(scanner, "Input Resident vip 1 or 2, where" +
                                                                             "1 - FALSE, " +
                                                                             "2 - TRUE", 2) != 1;
                final Gender gender =
                    EnumConverter.
                        integerToGender(InputDataReader.getIntegerInput(scanner, "Input Resident gender 1 or 2, where" +
                                                                                 "1 - MALE, " +
                                                                                 "2 - FEMALE", 2));
                residentController.createResident(new Resident(firstName, lastName, gender, vip, phone, null));
            } catch (final Exception e) {
                System.err.println(String.format("Failed to add a Resident! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.TOTAL_RESIDENTS, menu, () -> {
            final int residents = residentController.showCountResidents();
            System.out.println(String.format("Total residents: %d", residents));
        }));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.ATTENDANCE_TO_RESIDENT, menu, () -> {
            try {
                final List<Attendance> attendances = attendanceController.showAttendances();
                Printer.show(attendances, "attendance");

                final Integer attendanceId = InputDataReader
                    .getIntegerInput(scanner, "Input Attendance id...", attendances.size() - 1);

                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");

                final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

                residentController
                    .addAttendanceToResident(residents.get(residentId - 1), attendances.get(attendanceId - 1));
            } catch (final Exception e) {
                System.err
                    .println(String.format("Failed to add an Attendance to Resident! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterResidents(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.ALL_RESIDENTS, menu, () -> {
            final List<Resident> residents = residentController.showResidents();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.SORT_BY_NAME, menu, () -> {
            final List<Resident> residents = residentController.showResidentsSortedByName();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.SORT_BY_CHECK_OUT, menu, () -> {
            final List<Resident> residents = residentController.showResidentsSortedByCheckOutDate();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addHotelAdminSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CHECK_IN, menu, () -> {
            try {
                final List<Room> rooms = roomController.showAllRooms();
                Printer.show(rooms, "room");
                final Integer roomId = InputDataReader
                    .getIntegerInput(scanner, "Input Room id...", rooms.size() - 1);

                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");
                final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
                final LocalDate checkInDate = InputDataReader.getLocalDateInput(scanner, "Input check-in date...");
                final LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

                hotelController
                    .checkIn(residents.get(residentId - 1), rooms.get(roomId - 1), checkInDate, checkOutDate);
            } catch (final Exception e) {
                System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CHECK_OUT, menu, () -> {
            try {
                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");

                final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
                final LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

                hotelController.checkOut(residents.get(residentId - 1), checkOutDate);
            } catch (final ListEntitiesIsEmptyException ex) {
                System.err.println(ex);
            } catch (final Exception e) {
                System.err.println(String.format("Failed to check-out! Input valid parameters! %n%s%n", e));
            }
        }));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CALCULATE_BILL, menu, () -> {
            try {
                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");
                final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

                hotelController.calculateBill(residents.get(residentId - 1));
            } catch (final Exception e) {
                System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addRoomSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(RoomMenuImpl.SHOW_ROOMS.getTitle());
        this.addPrinterRoomsSubMenu(printerMenu, menu);
        menu.addMenuItem(new MenuItem(RoomMenuImpl.SHOW_ROOMS, printerMenu));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.CHANGE_ROOM_PRICE, menu, () -> {
            final Integer roomNumber =
                InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            final BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input new Room daily price..."));

            try {
                roomController.changePrice(roomNumber, dailyPrice);
            } catch (final EntityNotFoundException e) {
                System.err.println("Entity not found! " + e);
            }
        }));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.CHANGE_ROOM_STATUS, menu, () -> {
            try {
                final Integer roomNumber =
                    InputDataReader
                        .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
                final RoomStatus status = integerToStatus(
                    InputDataReader
                        .getIntegerInput(scanner,
                                         "Input the Room status, where\n " +
                                         "\t1 = VACANT, " +
                                         "\t2 = OCCUPIED, " +
                                         "\t3 = REPAIR...",
                                         RoomStatus.values().length));

                roomController.changeStatus(roomNumber, status);
            } catch (final Exception e) {
                System.err.println(String.format("Failed to change Room's status! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.TOTAL_VACANT_ROOMS, menu, () -> {
            final int vacantRooms = roomController.countVacantRooms();
            System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
        }));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.SHOW_DETAILS, menu, () -> {
            try {
                final Integer roomNumber =
                    InputDataReader.getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
                final Room room = roomController.showRoomDetails(roomNumber);
                Printer.show(room);
            } catch (final Exception e) {
                System.err.println(String.format("Failed to add a Room! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterRoomsSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.ALL_ROOMS, menu, () -> {
            final List<Room> rooms = roomController.showAllRooms();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_ACCOMMODATION, menu, () -> {
            final List<Room> rooms = roomController.showAllRoomsSortedByAccommodation();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_PRICE, menu, () -> {
            final List<Room> rooms = roomController.showAllRoomsSortedByPrice();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_STARS, menu, () -> {
            final List<Room> rooms = roomController.showAllRoomsSortedByStars();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_ROOMS, menu, () -> {
            final List<Room> rooms = roomController.showVacantRooms();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_ROOMS_ON_DATE, menu, () -> {
            final LocalDate date =
                InputDataReader.getLocalDateInput(scanner, "Input date with format \"YYYY-MM-DD\"...");
            final List<Room> rooms = roomController.showVacantRoomsOnDate(date);
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_ACCOMMODATION, menu, () -> {
            final List<Room> rooms = roomController.showVacantRoomsSortedByAccommodation();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_PRICE, menu, () -> {
            final List<Room> rooms = roomController.showVacantRoomsSortedByPrice();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_STARS, menu, () -> {
            final List<Room> rooms = roomController.showVacantRoomsSortedByStars();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
