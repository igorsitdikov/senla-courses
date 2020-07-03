package com.senla.hotel.ui.menu;

import java.util.List;

public class Navigator {
    private static Navigator navigator;
    private Menu currentMenu;

    private Navigator() {
    }

    public static Navigator getInstance() {
        if (navigator == null) {
            navigator = new Navigator();
        }
        return navigator;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        System.out.println(currentMenu.getName());
        List<MenuItem> items = currentMenu.getMenuItems();
        for (MenuItem item : items) {
            System.out.println(String.format("%d. %s",
                                             item.getMenuEnum().getId(),
                                             item.getMenuEnum().getTitle()));
        }
    }

    public void navigate(Integer index) {
        List<MenuItem> items = currentMenu.getMenuItems();
        if (items.get(index).getAction() != null) {
            items.get(index).doAction();
        } else {
            items.get(index).getNextMenu();
        }
    }
}
