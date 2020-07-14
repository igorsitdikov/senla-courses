package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.List;
import java.util.Scanner;

public final class MenuController {
    private static MenuController menuController;

    private MenuController() {
    }

    public static MenuController getInstance() {
        if (menuController == null) {
            menuController = new MenuController();
        }
        return menuController;
    }

    public void run() {
        HotelController.getInstance().importData();
        final Navigator navigator = Navigator.getInstance();
        final Builder builder = Builder.getInstance();

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        navigator.setCurrentMenu(builder.getMenu());
        navigator.printMenu();

        while (!exit) {

            int choice = InputDataReader.getIntegerInput(scanner) - 1;
            List<MenuItem> menuItems = navigator.getCurrentMenu().getMenuItems();

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
        HotelController.getInstance().exportData();
        System.out.println("Goodbye! Your changes have been saved!");
    }
}
