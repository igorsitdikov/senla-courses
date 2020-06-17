package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.attendance.ChangeAttendancePriceAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByNameAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByPriceAction;
import com.senla.hotel.ui.action.room.ChangePriceAction;
import com.senla.hotel.ui.action.room.CountVacantAction;
import com.senla.hotel.ui.action.room.CreateAttendanceAction;
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

    public Builder() {
        HotelController.getInstance();
    }

    public Menu buildMenu() {
        Menu roomMenu = new Menu("Room menu");
        Menu attendanceMenu = new Menu("Attendance menu");

        roomMenu.addMenuItem(new MenuItem("Add a room",
                                          roomMenu,
                                          new CreateAttendanceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms",
                                          roomMenu,
                                          new ShowRoomsAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by accommodation",
                                          roomMenu,
                                          new ShowRoomsSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by price",
                                          roomMenu,
                                          new ShowRoomsSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by stars",
                                          roomMenu,
                                          new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant",
                                          roomMenu,
                                          new ShowVacantAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant on date",
                                          roomMenu,
                                          new ShowVacantOnDateAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by accommodation",
                                          roomMenu,
                                          new ShowVacantSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by price",
                                          roomMenu,
                                          new ShowVacantSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by stars",
                                          roomMenu,
                                          new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print room's details",
                                          roomMenu,
                                          new ShowDetailsAction()));
        roomMenu.addMenuItem(new MenuItem("Change price",
                                          roomMenu,
                                          new ChangePriceAction()));
        roomMenu.addMenuItem(new MenuItem("Total vacant rooms",
                                          roomMenu,
                                          new CountVacantAction()));

        attendanceMenu.addMenuItem(new MenuItem("Create attendance",
                                                attendanceMenu,
                                                new CreateAttendanceAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show all attendance",
                                                attendanceMenu,
                                                new ShowAttendancesAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show attendances sorted by name",
                                                attendanceMenu,
                                                new ShowAttendancesSortedByNameAction()));
        attendanceMenu.addMenuItem(new MenuItem("Show attendances sorted by price",
                                                attendanceMenu,
                                                new ShowAttendancesSortedByPriceAction()));
        attendanceMenu.addMenuItem(new MenuItem("Change attendance price",
                                                attendanceMenu,
                                                new ChangeAttendancePriceAction()));


        rootMenu.addMenuItem(new MenuItem("Room menu", roomMenu));
        rootMenu.addMenuItem(new MenuItem("Attendance menu", attendanceMenu));
        rootMenu.addMenuItem(new MenuItem("Exit"));

        return rootMenu;
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
