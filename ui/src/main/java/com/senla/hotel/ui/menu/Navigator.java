package com.senla.hotel.ui.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Navigator {

    private static final Logger logger = LogManager.getLogger(Navigator.class);

    private Menu currentMenu;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(final Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        logger.info(currentMenu.getName());
        final List<MenuItem> items = currentMenu.getMenuItems();
        for (final MenuItem item : items) {
            System.out.printf("%s. %s%n",
                        item.getMenuEnum().getId(),
                        item.getMenuEnum().getTitle());
        }
    }

    public void navigate(final Integer index) {
        final List<MenuItem> items = currentMenu.getMenuItems();
        if (items.get(index).getAction() != null) {
            items.get(index).doAction();
        } else {
            items.get(index).getNextMenu();
        }
    }
}
