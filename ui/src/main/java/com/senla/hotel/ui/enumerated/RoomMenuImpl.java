package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum RoomMenuImpl implements MenuEnum {

    SHOW_ROOMS(1, "Show rooms"),
    ADD_ROOM(2, "Add a room"),
    CHANGE_ROOM_PRICE(3, "Change price"),
    CHANGE_ROOM_STATUS(4, "Change status"),
    TOTAL_VACANT_ROOMS(5, "Total vacant rooms"),
    SHOW_DETAILS(6, "Show room's details"),
    SHOW_LAST_RESIDENTS(7, "Show last residents"),
    EXPORT_ROOMS_TO_CSV(8, "Export rooms to csv"),
    IMPORT_ROOMS_FROM_CSV(9, "Import rooms from csv");

    private int id;
    private String title;

    RoomMenuImpl(final int id, final String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
