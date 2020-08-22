package com.senla.hotel.ui.menu;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.ui.action.admin.CalculateBillAction;
import com.senla.hotel.ui.action.admin.CheckInAction;
import com.senla.hotel.ui.action.admin.CheckOutAction;
import com.senla.hotel.ui.action.admin.ExportHistoryAction;
import com.senla.hotel.ui.action.admin.ImportHistoryAction;
import com.senla.hotel.ui.action.attendance.*;
import com.senla.hotel.ui.action.resident.*;
import com.senla.hotel.ui.action.room.*;
import com.senla.hotel.ui.enumerated.*;

@Singleton
public class Builder {
    private static final String MAIN_MENU = "Main menu";
    private static final String HOTEL_ADMIN_MENU = "Hotel admin menu";
    private static final String ROOM_MENU = "Room menu";
    private static final String RESIDENT_MENU = "Resident menu";
    private static final String ATTENDANCE_MENU = "Attendance menu";
    private Menu rootMenu = new Menu(MAIN_MENU);
    @Autowired
    private HotelController hotelController;
    @Autowired
    private RoomController roomController;
    @Autowired
    private AttendanceController attendanceController;
    @Autowired
    private ResidentController residentController;

    public Menu buildMenu() {
        Menu hotelAdminMenu = new Menu(HOTEL_ADMIN_MENU);
        Menu roomMenu = new Menu(ROOM_MENU);
        Menu residentMenu = new Menu(RESIDENT_MENU);
        Menu attendanceMenu = new Menu(ATTENDANCE_MENU);

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
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.ADD_ATTENDANCE, menu,
                new AddAttendanceAction(attendanceController)));
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.CHANGE_ATTENDANCE_PRICE, menu,
                new ChangeAttendancePriceAction(attendanceController)));
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.EXPORT_ATTENDANCES_TO_CSV, menu,
                new ExportAttendanceAction(attendanceController)));
        menu.addMenuItem(new MenuItem(AttendanceMenuImpl.IMPORT_ATTENDANCES_FROM_CSV, menu,
                new ImportAttendanceAction(attendanceController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterAttendances(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.ALL_ATTENDANCES, menu,
                new ShowAttendancesAction(attendanceController)));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.SORT_ATTENDANCES_BY_NAME, menu,
                new ShowAttendancesSortedByNameAction(attendanceController)));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenuImpl.SORT_ATTENDANCES_BY_PRICE, menu,
                new ShowAttendancesSortedByPriceAction(attendanceController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addResidentSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(ResidentMenuImpl.SHOW_RESIDENTS.getTitle());
        this.addPrinterResidents(printerMenu, menu);
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.SHOW_RESIDENTS, printerMenu));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.ADD_RESIDENT, menu,
                new AddResidentAction(residentController)));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.TOTAL_RESIDENTS, menu,
                new CountResidentsAction(residentController)));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.ATTENDANCE_TO_RESIDENT, menu,
                new AddAttendanceToResidentAction(attendanceController, residentController)));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.EXPORT_RESIDENTS_TO_CSV, menu,
                new ExportResidentAction(residentController)));
        menu.addMenuItem(new MenuItem(ResidentMenuImpl.IMPORT_RESIDENTS_FROM_CSV, menu,
                new ImportResidentAction(residentController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterResidents(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.ALL_RESIDENTS, menu,
                new ShowResidentsAction(residentController)));
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.SORT_BY_NAME, menu,
                new ShowResidentsSortedByNameAction(residentController)));
        menu.addMenuItem(new MenuItem(ShowResidentsMenuImpl.SORT_BY_CHECK_OUT, menu,
                new ShowResidentsSortedByCheckOutDateAction(residentController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addHotelAdminSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CHECK_IN, menu,
                new CheckInAction(residentController, hotelController, roomController)));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CHECK_OUT, menu,
                new CheckOutAction(residentController, hotelController)));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.CALCULATE_BILL, menu,
                new CalculateBillAction(residentController, hotelController)));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.EXPORT_HISTORIES_TO_CSV, menu,
                new ExportHistoryAction(hotelController)));
        menu.addMenuItem(new MenuItem(HotelAdminMenuImpl.IMPORT_HISTORIES_FROM_CSV, menu,
                new ImportHistoryAction(hotelController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addRoomSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(RoomMenuImpl.SHOW_ROOMS.getTitle());
        this.addPrinterRoomsSubMenu(printerMenu, menu);
        menu.addMenuItem(new MenuItem(RoomMenuImpl.SHOW_ROOMS, printerMenu));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.ADD_ROOM, menu,
                new AddRoomAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.CHANGE_ROOM_PRICE, menu,
                new ChangePriceAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.CHANGE_ROOM_STATUS, menu,
                new ChangeStatusAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.TOTAL_VACANT_ROOMS, menu,
                new CountVacantAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.SHOW_DETAILS, menu,
                new ShowDetailsAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.SHOW_LAST_RESIDENTS, menu,
                new ShowLastResidentsAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.EXPORT_ROOMS_TO_CSV, menu,
                new ExportRoomAction(roomController)));
        menu.addMenuItem(new MenuItem(RoomMenuImpl.IMPORT_ROOMS_FROM_CSV, menu,
                new ImportRoomAction(roomController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterRoomsSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.ALL_ROOMS, menu,
                new ShowRoomsAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_ACCOMMODATION, menu,
                new ShowRoomsSortedByAccommodationAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_PRICE, menu,
                new ShowRoomsSortedByPriceAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.SORT_BY_STARS, menu,
                new ShowRoomsSortedByStarsAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_ROOMS, menu,
                new ShowVacantAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_ROOMS_ON_DATE, menu,
                new ShowVacantOnDateAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_ACCOMMODATION, menu,
                new ShowVacantSortedByAccommodationAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_PRICE, menu,
                new ShowVacantSortedByPriceAction(roomController)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenuImpl.VACANT_SORT_BY_STARS, menu,
                new ShowVacantSortedByStarsAction(roomController)));
        menu.addMenuItem(new MenuItem(MainMenuImpl.TO_PREVIOUS_MENU, previous));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
