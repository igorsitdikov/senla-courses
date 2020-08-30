package com.senla.hotel.ui.menu;

import com.senla.hotel.annotation.Singleton;

import java.util.List;

@Singleton
public class Navigator {

    private Menu currentMenu;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(final Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        System.out.println(currentMenu.getName());
        final List<MenuItem> items = currentMenu.getMenuItems();
        for (final MenuItem item : items) {
            System.out.println(String.format("%d. %s",
                                             item.getMenuEnum().getId(),
                                             item.getMenuEnum().getTitle()));
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
