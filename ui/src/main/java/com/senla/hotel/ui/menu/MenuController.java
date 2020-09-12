package com.senla.hotel.ui.menu;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.utils.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component
public final class MenuController {

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    private final HotelController hotelController;
    private final Navigator navigator;
    private final Builder builder;
    private final Connector connector;

    public MenuController(final HotelController hotelController,
                          final Navigator navigator,
                          final Builder builder,
                          final Connector connector) {
        this.hotelController = hotelController;
        this.navigator = navigator;
        this.builder = builder;
        this.connector = connector;
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
        try {
            hotelController.exportData();
            connector.closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage());
        } catch (final PersistException e) {
            logger.error("Could not save data {}", e.getMessage());
        }

        logger.info("Goodbye! Your changes have been saved!");
    }
}
