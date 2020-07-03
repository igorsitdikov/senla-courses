package com.senla.hotel.ui.menu;


import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;
import com.senla.hotel.ui.interfaces.IAction;

public class MenuItem {
    private IMenuEnum menuEnum;
    private IAction action;
    private Menu nextMenu;

    public MenuItem(IMenuEnum menuEnum, Menu nextMenu) {
        this.menuEnum = menuEnum;
        this.nextMenu = nextMenu;
    }

    public MenuItem(IMenuEnum menuEnum, Menu nextMenu, IAction action) {
        this.menuEnum = menuEnum;
        this.nextMenu = nextMenu;
        this.action = action;
    }

    public void doAction() {
        this.action.execute();
    }

    public IMenuEnum getMenuEnum() {
        return menuEnum;
    }

    public void setMenuEnum(final IMenuEnum menuEnum) {
        this.menuEnum = menuEnum;
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

    public MenuItem(IMenuEnum menuEnum) {
        this.menuEnum = menuEnum;
    }
}
