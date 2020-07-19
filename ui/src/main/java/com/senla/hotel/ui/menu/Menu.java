package com.senla.hotel.ui.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private String name;

    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(final List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(final MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}
