package com.senla.hotel.ui.menu;


import com.senla.hotel.ui.interfaces.IAction;

public class MenuItem {
    private String name;
    private IAction action;
    private Menu nextMenu;

    public MenuItem(String name, Menu nextMenu) {
        this.name = name;
        this.nextMenu = nextMenu;
    }

    public MenuItem(String name, Menu nextMenu, IAction action) {
        this.name = name;
        this.nextMenu = nextMenu;
        this.action = action;
    }

    public void doAction() {
        this.action.execute();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(Menu nextMenu) {
        this.nextMenu = nextMenu;
    }

    public MenuItem(String name) {
        this.name = name;
    }
}
