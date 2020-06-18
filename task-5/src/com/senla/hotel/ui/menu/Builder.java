package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.admin.CalculateBillAction;
import com.senla.hotel.ui.action.admin.CheckInAction;
import com.senla.hotel.ui.action.admin.CheckOutAction;
import com.senla.hotel.ui.action.attendance.ChangeAttendancePriceAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByNameAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByPriceAction;
import com.senla.hotel.ui.action.resident.*;
import com.senla.hotel.ui.action.room.*;

public class Builder {
    private Menu rootMenu = new Menu("Main menu");

    public Builder() {
        HotelController.getInstance();
    }

    public Menu buildMenu() {
        Menu roomMenu = new Menu("Room menu");
        Menu attendanceMenu = new Menu("Attendance menu");
        Menu residentMenu = new Menu("Resident menu");
        Menu hotelAdminMenu = new Menu("Hotel admin menu");

        hotelAdminMenu.addMenuItem(new MenuItem("Check in resident",
                hotelAdminMenu, new CheckInAction()));
        hotelAdminMenu.addMenuItem(new MenuItem("Check out resident",
                hotelAdminMenu, new CheckOutAction()));
        hotelAdminMenu.addMenuItem(new MenuItem("Calculate resident's bill",
                hotelAdminMenu, new CalculateBillAction()));

        roomMenu.addMenuItem(new MenuItem("Add a room",
                roomMenu, new CreateRoomAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms",
                roomMenu, new ShowRoomsAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by accommodation",
                roomMenu, new ShowRoomsSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by price",
                roomMenu, new ShowRoomsSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by stars",
                roomMenu, new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant",
                roomMenu, new ShowVacantAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant on date",
                roomMenu, new ShowVacantOnDateAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by accommodation",
                roomMenu, new ShowVacantSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by price",
                roomMenu, new ShowVacantSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by stars",
                roomMenu, new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print room's details",
                roomMenu, new ShowDetailsAction()));
        roomMenu.addMenuItem(new MenuItem("Change price",
                roomMenu, new ChangePriceAction()));
        roomMenu.addMenuItem(new MenuItem("Total vacant rooms",
                roomMenu, new CountVacantAction()));

        attendanceMenu.addMenuItem(new MenuItem("Create attendance",
                attendanceMenu, new CreateRoomAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show all attendance",
                attendanceMenu, new ShowAttendancesAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show attendances sorted by name",
                attendanceMenu, new ShowAttendancesSortedByNameAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show attendances sorted by price",
                attendanceMenu, new ShowAttendancesSortedByPriceAction()));
        attendanceMenu.addMenuItem(new MenuItem("Change attendance price",
                attendanceMenu, new ChangeAttendancePriceAction()));

        residentMenu.addMenuItem(new MenuItem("Create resident",
                residentMenu, new CreateResidentAction()));
        residentMenu.addMenuItem(new MenuItem("Show all residents",
                residentMenu, new ShowResidentsAction()));
        residentMenu.addMenuItem(new MenuItem("Show all residents sorted by name",
                residentMenu, new ShowResidentsSortedByNameAction()));
        residentMenu.addMenuItem(new MenuItem("Show all residents sorted by check out date",
                residentMenu, new ShowResidentsSortedByCheckOutDateAction()));
        residentMenu.addMenuItem(new MenuItem("Total residents",
                residentMenu, new CountResidentsAction()));
        residentMenu.addMenuItem(new MenuItem("Add attendance to resident",
                residentMenu, new AddAttendanceToResidentAction()));

        rootMenu.addMenuItem(new MenuItem("Hotel admin menu", hotelAdminMenu));
        rootMenu.addMenuItem(new MenuItem("Room menu", roomMenu));
        rootMenu.addMenuItem(new MenuItem("Attendance menu", attendanceMenu));
        rootMenu.addMenuItem(new MenuItem("Exit"));

        return rootMenu;
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
