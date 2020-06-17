package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.action.room.*;

public class Builder {
    private Menu rootMenu = new Menu("Main menu");

    public Builder() {
        HotelController.getInstance();
    }

    public Menu buildMenu() {
        Menu roomMenu = new Menu("Room menu");

        roomMenu.addMenuItem(new MenuItem("Add a room", roomMenu, new AddAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms", roomMenu, new ShowAllAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by accommodation", roomMenu, new ShowAllSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by price", roomMenu, new ShowAllSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all rooms sorted by stars", roomMenu, new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant", roomMenu, new ShowVacantAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by accommodation", roomMenu, new ShowVacantSortedByAccommodationAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by price", roomMenu, new ShowVacantSortedByPriceAction()));
        roomMenu.addMenuItem(new MenuItem("Print all vacant rooms sorted by stars", roomMenu, new ShowVacantSortedByStarsAction()));
        roomMenu.addMenuItem(new MenuItem("Print room's details", roomMenu, new ShowDetailsAction()));

        rootMenu.addMenuItem(new MenuItem("Room menu", roomMenu));
        rootMenu.addMenuItem(new MenuItem("Exit"));

        return rootMenu;
    }

    public Menu getMenu() {
        return this.buildMenu();
    }
}
