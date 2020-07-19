package com.senla.hotel.ui.menu;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.Singleton;
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
import com.senla.hotel.ui.enumerated.AttendanceMenu;
import com.senla.hotel.ui.enumerated.HotelAdminMenu;
import com.senla.hotel.ui.enumerated.MainMenu;
import com.senla.hotel.ui.enumerated.ResidentMenu;
import com.senla.hotel.ui.enumerated.RoomMenu;
import com.senla.hotel.ui.enumerated.ShowAttendancesMenu;
import com.senla.hotel.ui.enumerated.ShowResidentsMenu;
import com.senla.hotel.ui.enumerated.ShowRoomsMenu;
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
    private Menu rootMenu = new Menu(MAIN_MENU);
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private AttendanceController attendanceController;
    @Autowired
    private ResidentController residentController;
    @Autowired
    private HotelController hotelController;
    @Autowired
    private RoomController roomController;

    public Menu buildMenu() {
        Menu hotelAdminMenu = new Menu(HOTEL_ADMIN_MENU);
        Menu roomMenu = new Menu(ROOM_MENU);
        Menu residentMenu = new Menu(RESIDENT_MENU);
        Menu attendanceMenu = new Menu(ATTENDANCE_MENU);

        this.addHotelAdminSubMenu(hotelAdminMenu, rootMenu);
        this.addRoomSubMenu(roomMenu, rootMenu);
        this.addResidentSubMenu(residentMenu, rootMenu);
        this.addAttendanceSubMenu(attendanceMenu, rootMenu);

        rootMenu.addMenuItem(new MenuItem(MainMenu.HOTEL_ADMIN, hotelAdminMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenu.ROOM, roomMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenu.RESIDENT, residentMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenu.ATTENDANCE, attendanceMenu));
        rootMenu.addMenuItem(new MenuItem(MainMenu.EXIT, null));

        return rootMenu;
    }

    private void addAttendanceSubMenu(final Menu menu, final Menu previous) {
        final Menu showerMenu = new Menu(AttendanceMenu.SHOW_ATTENDANCES.getTitle());
        this.addPrinterAttendances(showerMenu, menu);
        menu.addMenuItem(new MenuItem(AttendanceMenu.SHOW_ATTENDANCES, showerMenu));
        menu.addMenuItem(new MenuItem(AttendanceMenu.ADD_ATTENDANCE, menu, () -> {
            try {
                String name = InputDataReader.getStringInput(scanner);
                BigDecimal dailyPrice =
                    BigDecimal
                        .valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
                attendanceController.createAttendance(new Attendance(dailyPrice, name));
            } catch (Exception e) {
                System.err.println(String.format("Failed to add a Attendance! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(AttendanceMenu.CHANGE_ATTENDANCE_PRICE, menu, () -> {
            try {
                String name = InputDataReader.getStringInput(scanner);
                BigDecimal dailyPrice =
                    BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
                attendanceController.changePrice(name, dailyPrice);
            } catch (Exception e) {
                System.err.println(String.format("Failed to change price a Attendance! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterAttendances(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowAttendancesMenu.ALL_ATTENDANCES, menu, () -> {
            List<Attendance> attendances = attendanceController.showAttendances();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenu.SORT_ATTENDANCES_BY_NAME, menu, () -> {
            List<Attendance> attendances = attendanceController.showAttendancesSortedByName();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenu.SORT_ATTENDANCES_BY_PRICE, menu, () -> {
            List<Attendance> attendances =
                attendanceController.showAttendancesSortedByPrice();
            Printer.show(attendances);
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addResidentSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(ResidentMenu.SHOW_RESIDENTS.getTitle());
        this.addPrinterResidents(printerMenu, menu);
        menu.addMenuItem(new MenuItem(ResidentMenu.SHOW_RESIDENTS, printerMenu));
        menu.addMenuItem(new MenuItem(ResidentMenu.ADD_RESIDENT, menu, () -> {
            try {
                String firstName = InputDataReader.getStringInput(scanner);
                String lastName = InputDataReader.getStringInput(scanner);
                String phone = InputDataReader.getStringInput(scanner);
                Boolean vip = InputDataReader.getIntegerInput(scanner, "Input Resident vip 1 or 2, where" +
                                                                       "1 - FALSE, " +
                                                                       "2 - TRUE", 2) != 1;
                Gender gender =
                    EnumConverter.
                        integerToGender(InputDataReader.getIntegerInput(scanner, "Input Resident gender 1 or 2, where" +
                                                                                 "1 - MALE, " +
                                                                                 "2 - FEMALE", 2));
                residentController.createResident(new Resident(firstName, lastName, gender, vip, phone, null));
            } catch (Exception e) {
                System.err.println(String.format("Failed to add a Resident! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(ResidentMenu.TOTAL_RESIDENTS, menu, () -> {
            final int residents = residentController.showCountResidents();
            System.out.println(String.format("Total residents: %d", residents));
        }));
        menu.addMenuItem(new MenuItem(ResidentMenu.ATTENDANCE_TO_RESIDENT, menu, () -> {
            try {
                final List<Attendance> attendances = attendanceController.showAttendances();
                Printer.show(attendances, "attendance");

                Integer attendanceId = InputDataReader
                    .getIntegerInput(scanner, "Input Attendance id...", attendances.size() - 1);

                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");

                Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

                residentController
                    .addAttendanceToResident(residents.get(residentId - 1), attendances.get(attendanceId - 1));
            } catch (Exception e) {
                System.err
                    .println(String.format("Failed to add an Attendance to Resident! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterResidents(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowResidentsMenu.ALL_RESIDENTS, menu, () -> {
            List<Resident> residents = residentController.showResidents();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(ShowResidentsMenu.SORT_BY_NAME, menu, () -> {
            List<Resident> residents = residentController.showResidentsSortedByName();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(ShowResidentsMenu.SORT_BY_CHECK_OUT, menu, () -> {
            List<Resident> residents = residentController.showResidentsSortedByCheckOutDate();
            Printer.show(residents);
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addHotelAdminSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(HotelAdminMenu.CHECK_IN, menu, () -> {
            try {
                final List<Room> rooms = roomController.showAllRooms();
                Printer.show(rooms, "room");
                Integer roomId = InputDataReader
                    .getIntegerInput(scanner, "Input Room id...", rooms.size() - 1);

                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");
                Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
                LocalDate checkInDate = InputDataReader.getLocalDateInput(scanner, "Input check-in date...");
                LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

                hotelController
                    .checkIn(residents.get(residentId - 1), rooms.get(roomId - 1), checkInDate, checkOutDate);
            } catch (Exception e) {
                System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(HotelAdminMenu.CHECK_OUT, menu, () -> {
            try {
                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");

                Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
                LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

                hotelController.checkOut(residents.get(residentId - 1), checkOutDate);
            } catch (ListEntitiesIsEmptyException ex) {
                System.err.println(ex);
            } catch (Exception e) {
                System.err.println(String.format("Failed to check-out! Input valid parameters! %n%s%n", e));
            }
        }));
        menu.addMenuItem(new MenuItem(HotelAdminMenu.CALCULATE_BILL, menu, () -> {
            try {
                final List<Resident> residents = residentController.showResidents();
                Printer.show(residents, "resident");
                Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

                hotelController.calculateBill(residents.get(residentId - 1));
            } catch (Exception e) {
                System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addRoomSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(RoomMenu.SHOW_ROOMS.getTitle());
        this.addPrinterRoomsSubMenu(printerMenu, menu);
        menu.addMenuItem(new MenuItem(RoomMenu.SHOW_ROOMS, printerMenu));
        menu.addMenuItem(new MenuItem(RoomMenu.CHANGE_ROOM_PRICE, menu, () -> {
            Integer roomNumber =
                InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input new Room daily price..."));

            try {
                roomController.changePrice(roomNumber, dailyPrice);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        }));
        menu.addMenuItem(new MenuItem(RoomMenu.CHANGE_ROOM_STATUS, menu, () -> {
            try {
                Integer roomNumber =
                    InputDataReader
                        .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
                RoomStatus status = integerToStatus(
                    InputDataReader
                        .getIntegerInput(scanner,
                                         "Input the Room status, where\n " +
                                         "\t1 = VACANT, " +
                                         "\t2 = OCCUPIED, " +
                                         "\t3 = REPAIR...",
                                         RoomStatus.values().length));

                roomController.changeStatus(roomNumber, status);
            } catch (Exception e) {
                System.err.println(String.format("Failed to change Room's status! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(RoomMenu.TOTAL_VACANT_ROOMS, menu, () -> {
            final int vacantRooms = roomController.countVacantRooms();
            System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
        }));
        menu.addMenuItem(new MenuItem(RoomMenu.SHOW_DETAILS, menu, () -> {
            try {
                Integer roomNumber =
                    InputDataReader.getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
                Room room = roomController.showRoomDetails(roomNumber);
                Printer.show(room);
            } catch (Exception e) {
                System.err.println(String.format("Failed to add a Room! Input valid parameters! %s", e));
            }
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterRoomsSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.ALL_ROOMS, menu, () -> {
            List<Room> rooms = roomController.showAllRooms();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.SORT_BY_ACCOMMODATION, menu, () -> {
            List<Room> rooms = roomController.showAllRoomsSortedByAccommodation();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.SORT_BY_PRICE, menu, () -> {
            List<Room> rooms = roomController.showAllRoomsSortedByPrice();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.SORT_BY_STARS, menu, () -> {
            List<Room> rooms = roomController.showAllRoomsSortedByStars();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_ROOMS, menu, () -> {
            List<Room> rooms = roomController.showVacantRooms();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_ROOMS_ON_DATE, menu, () -> {
            LocalDate date = InputDataReader.getLocalDateInput(scanner, "Input date with format \"YYYY-MM-DD\"...");
            List<Room> rooms = roomController.showVacantRoomsOnDate(date);
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_ACCOMMODATION, menu, () -> {
            List<Room> rooms = roomController.showVacantRoomsSortedByAccommodation();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_PRICE, menu, () -> {
            List<Room> rooms = roomController.showVacantRoomsSortedByPrice();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_STARS, menu, () -> {
            List<Room> rooms = roomController.showVacantRoomsSortedByStars();
            Printer.show(rooms);
        }));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
