package com.senla.hotel.ui;

import com.senla.hotel.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}
