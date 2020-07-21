package com.senla.hotel.ui;

import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.ui.menu.MenuController;

public class Main {
    public static void main(final String[] args) throws Exception {
        final AppContext context = Application.run("com.senla.hotel");
        final MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
