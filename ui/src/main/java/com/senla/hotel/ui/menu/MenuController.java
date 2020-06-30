package com.senla.hotel.ui.menu;

import com.senla.hotel.ui.utils.InputDataReader;

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
        final Navigator navigator = Navigator.getInstance();
        final Builder builder = Builder.getInstance();

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        navigator.setCurrentMenu(builder.getMenu());
        navigator.printMenu();

        while (!exit) {

            int choice = InputDataReader.getIntegerInput(scanner) - 1;

            if (choice >= navigator.getCurrentMenu().getMenuItems().size()) {
                System.out.println("Incorrect choice. Try again");
                continue;
            } else {
                navigator.navigate(choice);
            }

            if (navigator.getCurrentMenu().getMenuItems().get(choice).getNextMenu() == null) {
                exit = true;
                continue;
            }

            navigator.setCurrentMenu(navigator.getCurrentMenu().getMenuItems().get(choice).getNextMenu());
            navigator.printMenu();
        }
        scanner.close();
        System.out.println("Goodbye! Your changes have been saved!");
    }
}
