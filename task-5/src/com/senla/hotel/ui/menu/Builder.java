package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.room.AddAction;
import com.senla.hotel.ui.action.room.ChangePriceAction;
import com.senla.hotel.ui.action.room.CountVacantAction;
import com.senla.hotel.ui.action.room.ShowAllAction;
import com.senla.hotel.ui.action.room.ShowAllSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowAllSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowDetailsAction;
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

        roomMenu.addMenuItem(new MenuItem("Add a room",
                                          roomMenu,
                                          new AddAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms",
                                          roomMenu,
                                          new ShowAllAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by accommodation",
                                          roomMenu,
                                          new ShowAllSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by price",
                                          roomMenu,
                                          new ShowAllSortedByPriceAction()));
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

        rootMenu.addMenuItem(new MenuItem("Room menu", roomMenu));
        rootMenu.addMenuItem(new MenuItem("Exit"));

        return rootMenu;
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
