package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum MainMenu implements IMenuEnum {
    TO_PREVIOUS_MENU(0, "Previous menu"),
    HOTEL_ADMIN(1, "Hotel admin menu"),
    ROOM(2, "Room menu"),
    RESIDENT(3, "Resident menu"),
    ATTENDANCE(4, "Attendance menu"),
    EXIT(5, "Exit");

    private int id;
    private String name;

    MainMenu(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
