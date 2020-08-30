package com.senla.hotel.ui.menu;

import com.senla.hotel.annotation.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Singleton
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
            logger.info("{}. {}",
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
