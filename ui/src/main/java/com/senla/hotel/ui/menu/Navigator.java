package com.senla.hotel.ui.menu;

import com.senla.annotaion.Singleton;

import java.util.List;

@Singleton
public class Navigator {
    private Menu currentMenu;

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
