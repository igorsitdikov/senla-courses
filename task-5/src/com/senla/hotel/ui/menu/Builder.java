package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.admin.CalculateBillAction;
import com.senla.hotel.ui.action.admin.CheckInAction;
import com.senla.hotel.ui.action.admin.CheckOutAction;
import com.senla.hotel.ui.action.attendance.ChangeAttendancePriceAction;
import com.senla.hotel.ui.action.attendance.CreateAttendanceAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByNameAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByPriceAction;
import com.senla.hotel.ui.action.resident.AddAttendanceToResidentAction;
import com.senla.hotel.ui.action.resident.CountResidentsAction;
import com.senla.hotel.ui.action.resident.CreateResidentAction;
import com.senla.hotel.ui.action.resident.ShowResidentsAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByCheckOutDateAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByNameAction;
import com.senla.hotel.ui.action.room.ChangePriceAction;
import com.senla.hotel.ui.action.room.CountVacantAction;
import com.senla.hotel.ui.action.room.CreateRoomAction;
import com.senla.hotel.ui.action.room.ShowDetailsAction;
import com.senla.hotel.ui.action.room.ShowRoomsAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowVacantAction;
import com.senla.hotel.ui.action.room.ShowVacantOnDateAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByStarsAction;

public class Builder {
    private Menu rootMenu = new Menu("Main menu");
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
        Menu roomMenu = new Menu("Room menu");
        Menu attendanceMenu = new Menu("Attendance menu");
        Menu residentMenu = new Menu("Resident menu");
        Menu hotelAdminMenu = new Menu("Hotel admin menu");

        addHotelAdminSubMenu(hotelAdminMenu);
        addRoomSubMenu(roomMenu);
        addResidentSubMenu(residentMenu);
        addAttendanceSubMenu(attendanceMenu);

        rootMenu.addMenuItem(new MenuItem("Hotel admin menu", hotelAdminMenu));
        rootMenu.addMenuItem(new MenuItem("Room menu", roomMenu));
        rootMenu.addMenuItem(new MenuItem("Resident menu", residentMenu));
        rootMenu.addMenuItem(new MenuItem("Attendance menu", attendanceMenu));
        rootMenu.addMenuItem(new MenuItem("Exit"));

        return rootMenu;
    }

    private void addAttendanceSubMenu(final Menu menu) {
        menu.addMenuItem(new MenuItem("Create attendance",
                                      menu, new CreateAttendanceAction()));
        menu.addMenuItem(new MenuItem("Show all attendance",
                                      menu, new ShowAttendancesAction()));
        menu.addMenuItem(new MenuItem("Show attendances sorted by name",
                                      menu, new ShowAttendancesSortedByNameAction()));
        menu.addMenuItem(new MenuItem("Show attendances sorted by price",
                                      menu, new ShowAttendancesSortedByPriceAction()));
        menu.addMenuItem(new MenuItem("Change attendance price",
                                      menu, new ChangeAttendancePriceAction()));
    }

    private void addResidentSubMenu(final Menu menu) {
        menu.addMenuItem(new MenuItem("Create resident",
                                      menu, new CreateResidentAction()));
        menu.addMenuItem(new MenuItem("Show all residents",
                                      menu, new ShowResidentsAction()));
        menu.addMenuItem(new MenuItem("Show all residents sorted by name",
                                      menu, new ShowResidentsSortedByNameAction()));
        menu.addMenuItem(new MenuItem("Show all residents sorted by check out date",
                                      menu, new ShowResidentsSortedByCheckOutDateAction()));
        menu.addMenuItem(new MenuItem("Total residents",
                                      menu, new CountResidentsAction()));
        menu.addMenuItem(new MenuItem("Add attendance to resident",
                                      menu, new AddAttendanceToResidentAction()));
    }

    private void addHotelAdminSubMenu(final Menu menu) {
        menu.addMenuItem(new MenuItem("Check in resident",
                                      menu, new CheckInAction()));
        menu.addMenuItem(new MenuItem("Check out resident",
                                      menu, new CheckOutAction()));
        menu.addMenuItem(new MenuItem("Calculate resident's bill",
                                      menu, new CalculateBillAction()));
    }

    private void addRoomSubMenu(final Menu menu) {
        menu.addMenuItem(new MenuItem("Add a room",
                                      menu, new CreateRoomAction()));
        menu.addMenuItem(new MenuItem("Print all rooms",
                                      menu, new ShowRoomsAction()));
        menu.addMenuItem(new MenuItem("Print all rooms sorted by accommodation",
                                      menu, new ShowRoomsSortedByAccommodationAction()));
        menu.addMenuItem(new MenuItem("Print all rooms sorted by price",
                                      menu, new ShowRoomsSortedByPriceAction()));
        menu.addMenuItem(new MenuItem("Print all rooms sorted by stars",
                                      menu, new ShowVacantSortedByStarsAction()));
        menu.addMenuItem(new MenuItem("Print all vacant",
                                      menu, new ShowVacantAction()));
        menu.addMenuItem(new MenuItem("Print all vacant on date",
                                      menu, new ShowVacantOnDateAction()));
        menu.addMenuItem(new MenuItem("Print all vacant rooms sorted by accommodation",
                                      menu, new ShowVacantSortedByAccommodationAction()));
        menu.addMenuItem(new MenuItem("Print all vacant rooms sorted by price",
                                      menu, new ShowVacantSortedByPriceAction()));
        menu.addMenuItem(new MenuItem("Print all vacant rooms sorted by stars",
                                      menu, new ShowVacantSortedByStarsAction()));
        menu.addMenuItem(new MenuItem("Print room's details",
                                      menu, new ShowDetailsAction()));
        menu.addMenuItem(new MenuItem("Change price",
                                      menu, new ChangePriceAction()));
        menu.addMenuItem(new MenuItem("Total vacant rooms",
                                      menu, new CountVacantAction()));
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
