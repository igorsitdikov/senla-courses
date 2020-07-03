package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum RoomMenu implements IMenuEnum {
    SHOW_ROOMS(1, "Show rooms"),
    ADD_ROOM(2, "Add a room"),
    CHANGE_ROOM_PRICE(3, "Change price"),
    CHANGE_ROOM_STATUS(4, "Change status"),
    TOTAL_VACANT_ROOMS(5, "Total vacant rooms"),
    SHOW_DETAILS(6, "Show room's details");

    private int id;
    private String name;

    RoomMenu(final int id, final String name) {
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
