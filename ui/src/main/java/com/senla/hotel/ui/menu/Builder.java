package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.admin.CalculateBillAction;
import com.senla.hotel.ui.action.admin.CheckInAction;
import com.senla.hotel.ui.action.admin.CheckOutAction;
import com.senla.hotel.ui.action.attendance.AddAttendanceAction;
import com.senla.hotel.ui.action.attendance.ChangeAttendancePriceAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByNameAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByPriceAction;
import com.senla.hotel.ui.action.resident.AddAttendanceToResidentAction;
import com.senla.hotel.ui.action.resident.AddResidentAction;
import com.senla.hotel.ui.action.resident.CountResidentsAction;
import com.senla.hotel.ui.action.resident.ShowResidentsAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByCheckOutDateAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByNameAction;
import com.senla.hotel.ui.action.room.AddRoomAction;
import com.senla.hotel.ui.action.room.ChangePriceAction;
import com.senla.hotel.ui.action.room.ChangeStatusAction;
import com.senla.hotel.ui.action.room.CountVacantAction;
import com.senla.hotel.ui.action.room.ShowDetailsAction;
import com.senla.hotel.ui.action.room.ShowRoomsAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByStarsAction;
import com.senla.hotel.ui.action.room.ShowVacantAction;
import com.senla.hotel.ui.action.room.ShowVacantOnDateAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByStarsAction;

import static com.senla.hotel.ui.enumerated.AttendanceMenu.ADD_ATTENDANCE;
import static com.senla.hotel.ui.enumerated.AttendanceMenu.CHANGE_ATTENDANCE_PRICE;
import static com.senla.hotel.ui.enumerated.AttendanceMenu.SHOW_ATTENDANCES;
import static com.senla.hotel.ui.enumerated.HotelAdminMenu.CALCULATE_BILL;
import static com.senla.hotel.ui.enumerated.HotelAdminMenu.CHECK_IN;
import static com.senla.hotel.ui.enumerated.HotelAdminMenu.CHECK_OUT;
import static com.senla.hotel.ui.enumerated.MainMenu.ATTENDANCE;
import static com.senla.hotel.ui.enumerated.MainMenu.EXIT;
import static com.senla.hotel.ui.enumerated.MainMenu.HOTEL_ADMIN;
import static com.senla.hotel.ui.enumerated.MainMenu.RESIDENT;
import static com.senla.hotel.ui.enumerated.MainMenu.ROOM;
import static com.senla.hotel.ui.enumerated.MainMenu.TO_PREVIOUS_MENU;
import static com.senla.hotel.ui.enumerated.ResidentMenu.ADD_RESIDENT;
import static com.senla.hotel.ui.enumerated.ResidentMenu.ATTENDANCE_TO_RESIDENT;
import static com.senla.hotel.ui.enumerated.ResidentMenu.SHOW_RESIDENTS;
import static com.senla.hotel.ui.enumerated.ResidentMenu.TOTAL_RESIDENTS;
import static com.senla.hotel.ui.enumerated.RoomMenu.ADD_ROOM;
import static com.senla.hotel.ui.enumerated.RoomMenu.CHANGE_ROOM_PRICE;
import static com.senla.hotel.ui.enumerated.RoomMenu.CHANGE_ROOM_STATUS;
import static com.senla.hotel.ui.enumerated.RoomMenu.SHOW_DETAILS;
import static com.senla.hotel.ui.enumerated.RoomMenu.SHOW_ROOMS;
import static com.senla.hotel.ui.enumerated.RoomMenu.TOTAL_VACANT_ROOMS;
import static com.senla.hotel.ui.enumerated.ShowAttendancesMenu.ALL_ATTENDANCES;
import static com.senla.hotel.ui.enumerated.ShowAttendancesMenu.SORT_ATTENDANCES_BY_NAME;
import static com.senla.hotel.ui.enumerated.ShowAttendancesMenu.SORT_ATTENDANCES_BY_PRICE;
import static com.senla.hotel.ui.enumerated.ShowResidentsMenu.ALL_RESIDENTS;
import static com.senla.hotel.ui.enumerated.ShowResidentsMenu.SORT_BY_CHECK_OUT;
import static com.senla.hotel.ui.enumerated.ShowResidentsMenu.SORT_BY_NAME;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.ALL_ROOMS;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.SORT_BY_ACCOMMODATION;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.SORT_BY_PRICE;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.SORT_BY_STARS;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.VACANT_ROOMS;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.VACANT_ROOMS_ON_DATE;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.VACANT_SORT_BY_ACCOMMODATION;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.VACANT_SORT_BY_PRICE;
import static com.senla.hotel.ui.enumerated.ShowRoomsMenu.VACANT_SORT_BY_STARS;

public class Builder {
    private static final String MAIN_MENU = "Main menu";
    private static final String HOTEL_ADMIN_MENU = "Hotel admin menu";
    private static final String ROOM_MENU = "Room menu";
    private static final String RESIDENT_MENU = "Resident menu";
    private static final String ATTENDANCE_MENU = "Attendance menu";
    private Menu rootMenu = new Menu(MAIN_MENU);
    private static Builder builder;

    private Builder() {
        HotelController.getInstance();
    }

    public static Builder getInstance() {
        if (builder == null) {
            builder = new Builder();
        }
        return builder;
    }

    public Menu buildMenu() {
        Menu hotelAdminMenu = new Menu(HOTEL_ADMIN_MENU);
        Menu roomMenu = new Menu(ROOM_MENU);
        Menu residentMenu = new Menu(RESIDENT_MENU);
        Menu attendanceMenu = new Menu(ATTENDANCE_MENU);

        this.addHotelAdminSubMenu(hotelAdminMenu, rootMenu);
        this.addRoomSubMenu(roomMenu, rootMenu);
        this.addResidentSubMenu(residentMenu, rootMenu);
        this.addAttendanceSubMenu(attendanceMenu, rootMenu);

        rootMenu.addMenuItem(new MenuItem(HOTEL_ADMIN, hotelAdminMenu));
        rootMenu.addMenuItem(new MenuItem(ROOM, roomMenu));
        rootMenu.addMenuItem(new MenuItem(RESIDENT, residentMenu));
        rootMenu.addMenuItem(new MenuItem(ATTENDANCE, attendanceMenu));
        rootMenu.addMenuItem(new MenuItem(EXIT, null));

        return rootMenu;
    }

    private void addAttendanceSubMenu(final Menu menu, final Menu previous) {
        final Menu showerMenu = new Menu(SHOW_ATTENDANCES.getTitle());
        this.addPrinterAttendances(showerMenu, menu);
        menu.addMenuItem(new MenuItem(SHOW_ATTENDANCES, showerMenu));
        menu.addMenuItem(new MenuItem(ADD_ATTENDANCE, menu, new AddAttendanceAction()));
        menu.addMenuItem(new MenuItem(CHANGE_ATTENDANCE_PRICE, menu, new ChangeAttendancePriceAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }


    private void addPrinterAttendances(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ALL_ATTENDANCES, menu, new ShowAttendancesAction()));
        menu.addMenuItem(new MenuItem(SORT_ATTENDANCES_BY_NAME, menu, new ShowAttendancesSortedByNameAction()));
        menu.addMenuItem(new MenuItem(SORT_ATTENDANCES_BY_PRICE, menu, new ShowAttendancesSortedByPriceAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    private void addResidentSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(SHOW_RESIDENTS.getTitle());
        this.addPrinterResidents(printerMenu, menu);
        menu.addMenuItem(new MenuItem(SHOW_RESIDENTS, printerMenu));
        menu.addMenuItem(new MenuItem(ADD_RESIDENT, menu, new AddResidentAction()));
        menu.addMenuItem(new MenuItem(TOTAL_RESIDENTS, menu, new CountResidentsAction()));
        menu.addMenuItem(new MenuItem(ATTENDANCE_TO_RESIDENT, menu, new AddAttendanceToResidentAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterResidents(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ALL_RESIDENTS, menu, new ShowResidentsAction()));
        menu.addMenuItem(new MenuItem(SORT_BY_NAME, menu, new ShowResidentsSortedByNameAction()));
        menu.addMenuItem(new MenuItem(SORT_BY_CHECK_OUT, menu, new ShowResidentsSortedByCheckOutDateAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    private void addHotelAdminSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(CHECK_IN, menu, new CheckInAction()));
        menu.addMenuItem(new MenuItem(CHECK_OUT, menu, new CheckOutAction()));
        menu.addMenuItem(new MenuItem(CALCULATE_BILL, menu, new CalculateBillAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    private void addRoomSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(SHOW_ROOMS.getTitle());
        this.addPrinterRoomsSubMenu(printerMenu, menu);
        menu.addMenuItem(new MenuItem(SHOW_ROOMS, printerMenu));
        menu.addMenuItem(new MenuItem(ADD_ROOM, menu, new AddRoomAction()));
        menu.addMenuItem(new MenuItem(CHANGE_ROOM_PRICE, menu, new ChangePriceAction()));
        menu.addMenuItem(new MenuItem(CHANGE_ROOM_STATUS, menu, new ChangeStatusAction()));
        menu.addMenuItem(new MenuItem(TOTAL_VACANT_ROOMS, menu, new CountVacantAction()));
        menu.addMenuItem(new MenuItem(SHOW_DETAILS, menu, new ShowDetailsAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterRoomsSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ALL_ROOMS, menu, new ShowRoomsAction()));
        menu.addMenuItem(new MenuItem(SORT_BY_ACCOMMODATION, menu, new ShowRoomsSortedByAccommodationAction()));
        menu.addMenuItem(new MenuItem(SORT_BY_PRICE, menu, new ShowRoomsSortedByPriceAction()));
        menu.addMenuItem(new MenuItem(SORT_BY_STARS, menu, new ShowRoomsSortedByStarsAction()));
        menu.addMenuItem(new MenuItem(VACANT_ROOMS, menu, new ShowVacantAction()));
        menu.addMenuItem(new MenuItem(VACANT_ROOMS_ON_DATE, menu, new ShowVacantOnDateAction()));
        menu.addMenuItem(new MenuItem(VACANT_SORT_BY_ACCOMMODATION, menu, new ShowVacantSortedByAccommodationAction()));
        menu.addMenuItem(new MenuItem(VACANT_SORT_BY_PRICE, menu, new ShowVacantSortedByPriceAction()));
        menu.addMenuItem(new MenuItem(VACANT_SORT_BY_STARS, menu, new ShowVacantSortedByStarsAction()));
        menu.addMenuItem(new MenuItem(TO_PREVIOUS_MENU, previous));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
