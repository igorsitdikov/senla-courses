package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum ShowRoomsMenu implements IMenuEnum {
    ALL_ROOMS(1, "Show all rooms"),
    SORT_BY_ACCOMMODATION(2, "Show all rooms sorted by accommodation"),
    SORT_BY_PRICE(3, "Show all rooms sorted by price"),
    SORT_BY_STARS(4, "Show all rooms sorted by stars"),
    VACANT_ROOMS(5, "Show all vacant rooms"),
    VACANT_ROOMS_ON_DATE(6, "Show all vacant rooms on date"),
    VACANT_SORT_BY_ACCOMMODATION(7, "Show all vacant rooms sorted by accommodation"),
    VACANT_SORT_BY_PRICE(8, "Show all vacant rooms sorted by price"),
    VACANT_SORT_BY_STARS(9, "Show all vacant rooms sorted by stars");

    private int id;
    private String name;

    ShowRoomsMenu(final int id, final String name) {
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
