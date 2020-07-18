package com.senla.hotel.ui.menu;

import com.senla.anntotaion.Singleton;
import com.senla.hotel.AppContext;
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
import com.senla.hotel.ui.enumerated.AttendanceMenu;
import com.senla.hotel.ui.enumerated.HotelAdminMenu;
import com.senla.hotel.ui.enumerated.MainMenu;
import com.senla.hotel.ui.enumerated.ResidentMenu;
import com.senla.hotel.ui.enumerated.RoomMenu;
import com.senla.hotel.ui.enumerated.ShowAttendancesMenu;
import com.senla.hotel.ui.enumerated.ShowResidentsMenu;
import com.senla.hotel.ui.enumerated.ShowRoomsMenu;

@Singleton
public class Builder {
    private static final String MAIN_MENU = "Main menu";
    private static final String HOTEL_ADMIN_MENU = "Hotel admin menu";
    private static final String ROOM_MENU = "Room menu";
    private static final String RESIDENT_MENU = "Resident menu";
    private static final String ATTENDANCE_MENU = "Attendance menu";
    private Menu rootMenu = new Menu(MAIN_MENU);

    private AppContext context;

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
        menu.addMenuItem(
            new MenuItem(AttendanceMenu.ADD_ATTENDANCE, menu, context.getObject(AddAttendanceAction.class)));
        menu.addMenuItem(new MenuItem(AttendanceMenu.CHANGE_ATTENDANCE_PRICE, menu,
                                      context.getObject(ChangeAttendancePriceAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterAttendances(final Menu menu, final Menu previous) {
        menu.addMenuItem(
            new MenuItem(ShowAttendancesMenu.ALL_ATTENDANCES, menu, context.getObject(ShowAttendancesAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowAttendancesMenu.SORT_ATTENDANCES_BY_NAME, menu,
                         context.getObject(ShowAttendancesSortedByNameAction.class)));
        menu.addMenuItem(new MenuItem(ShowAttendancesMenu.SORT_ATTENDANCES_BY_PRICE, menu,
                                      context.getObject(ShowAttendancesSortedByPriceAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addResidentSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(ResidentMenu.SHOW_RESIDENTS.getTitle());
        this.addPrinterResidents(printerMenu, menu);
        menu.addMenuItem(new MenuItem(ResidentMenu.SHOW_RESIDENTS, printerMenu));
        menu.addMenuItem(new MenuItem(ResidentMenu.ADD_RESIDENT, menu, context.getObject(AddResidentAction.class)));
        menu.addMenuItem(
            new MenuItem(ResidentMenu.TOTAL_RESIDENTS, menu, context.getObject(CountResidentsAction.class)));
        menu.addMenuItem(new MenuItem(ResidentMenu.ATTENDANCE_TO_RESIDENT, menu,
                                      context.getObject(AddAttendanceToResidentAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterResidents(final Menu menu, final Menu previous) {
        menu.addMenuItem(
            new MenuItem(ShowResidentsMenu.ALL_RESIDENTS, menu, context.getObject(ShowResidentsAction.class)));
        menu.addMenuItem(new MenuItem(ShowResidentsMenu.SORT_BY_NAME, menu,
                                      context.getObject(ShowResidentsSortedByNameAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowResidentsMenu.SORT_BY_CHECK_OUT, menu,
                         context.getObject(ShowResidentsSortedByCheckOutDateAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addHotelAdminSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(HotelAdminMenu.CHECK_IN, menu, context.getObject(CheckInAction.class)));
        menu.addMenuItem(new MenuItem(HotelAdminMenu.CHECK_OUT, menu, context.getObject(CheckOutAction.class)));
        menu.addMenuItem(
            new MenuItem(HotelAdminMenu.CALCULATE_BILL, menu, context.getObject(CalculateBillAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addRoomSubMenu(final Menu menu, final Menu previous) {
        final Menu printerMenu = new Menu(RoomMenu.SHOW_ROOMS.getTitle());
        this.addPrinterRoomsSubMenu(printerMenu, menu);
        menu.addMenuItem(new MenuItem(RoomMenu.SHOW_ROOMS, printerMenu));
        menu.addMenuItem(new MenuItem(RoomMenu.ADD_ROOM, menu, context.getObject(AddRoomAction.class)));
        menu.addMenuItem(new MenuItem(RoomMenu.CHANGE_ROOM_PRICE, menu, context.getObject(ChangePriceAction.class)));
        menu.addMenuItem(new MenuItem(RoomMenu.CHANGE_ROOM_STATUS, menu, context.getObject(ChangeStatusAction.class)));
        menu.addMenuItem(new MenuItem(RoomMenu.TOTAL_VACANT_ROOMS, menu, context.getObject(CountVacantAction.class)));
        menu.addMenuItem(new MenuItem(RoomMenu.SHOW_DETAILS, menu, context.getObject(ShowDetailsAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    private void addPrinterRoomsSubMenu(final Menu menu, final Menu previous) {
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.ALL_ROOMS, menu, context.getObject(ShowRoomsAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowRoomsMenu.SORT_BY_ACCOMMODATION, menu,
                         context.getObject(ShowRoomsSortedByAccommodationAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowRoomsMenu.SORT_BY_PRICE, menu, context.getObject(ShowRoomsSortedByPriceAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowRoomsMenu.SORT_BY_STARS, menu, context.getObject(ShowRoomsSortedByStarsAction.class)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_ROOMS, menu, context.getObject(ShowVacantAction.class)));
        menu.addMenuItem(
            new MenuItem(ShowRoomsMenu.VACANT_ROOMS_ON_DATE, menu, context.getObject(ShowVacantOnDateAction.class)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_ACCOMMODATION, menu,
                                      context.getObject(ShowVacantSortedByAccommodationAction.class)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_PRICE, menu,
                                      context.getObject(ShowVacantSortedByPriceAction.class)));
        menu.addMenuItem(new MenuItem(ShowRoomsMenu.VACANT_SORT_BY_STARS, menu,
                                      context.getObject(ShowVacantSortedByStarsAction.class)));
        menu.addMenuItem(new MenuItem(MainMenu.TO_PREVIOUS_MENU, previous));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }

    public void setContext(final AppContext context) {
        this.context = context;
    }

}
