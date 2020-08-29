package com.senla.hotel.ui.menu;


import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;
import com.senla.hotel.ui.interfaces.Action;

public class MenuItem {

    private MenuEnum menuEnum;
    private Action action;
    private Menu nextMenu;

    public MenuItem(final MenuEnum menuEnum, final Menu nextMenu) {
        this.menuEnum = menuEnum;
        this.nextMenu = nextMenu;
    }

    public MenuItem(final MenuEnum menuEnum, final Menu nextMenu, final Action action) {
        this.menuEnum = menuEnum;
        this.nextMenu = nextMenu;
        this.action = action;
    }

    public void doAction() {
        this.action.execute();
    }

    public MenuEnum getMenuEnum() {
        return menuEnum;
    }

    public void setMenuEnum(final MenuEnum menuEnum) {
        this.menuEnum = menuEnum;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(final Menu nextMenu) {
        this.nextMenu = nextMenu;
    }

    public MenuItem(final MenuEnum menuEnum) {
        this.menuEnum = menuEnum;
    }
}
