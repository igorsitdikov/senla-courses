package com.senla.hotel.ui.menu;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.List;
import java.util.Scanner;

@Singleton
public final class MenuController {
    @Autowired
    private HotelController hotelController;
    @Autowired
    private Navigator navigator;
    @Autowired
    private Builder builder;

    public static void main(final String[] args) throws Exception {
        final AppContext context = Application.run("com.senla.hotel");
        context.getObject(MenuController.class).run();
    }

    public void run() {
        hotelController.importData();
        final Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        navigator.setCurrentMenu(builder.getMenu());
        navigator.printMenu();

        while (!exit) {

            int choice = InputDataReader.getIntegerInput(scanner) - 1;
            final List<MenuItem> menuItems = navigator.getCurrentMenu().getMenuItems();

            if (choice == -1) {
                choice = menuItems.size() - 1;
            }

            if (choice >= menuItems.size() || choice < 0) {
                System.out.println("Incorrect choice. Try again");
                continue;
            } else {
                navigator.navigate(choice);
            }

            if (menuItems.get(choice).getNextMenu() == null) {
                exit = true;
                continue;
            }

            navigator.setCurrentMenu(menuItems.get(choice).getNextMenu());
            navigator.printMenu();
        }
        scanner.close();
        hotelController.exportData();
        System.out.println("Goodbye! Your changes have been saved!");
    }
}
