package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum ShowAttendancesMenu implements IMenuEnum {
    ALL_ATTENDANCES(1, "Show all attendances"),
    SORT_ATTENDANCES_BY_NAME(2, "Show attendances sorted by name"),
    SORT_ATTENDANCES_BY_PRICE(3, "Show attendances sorted by price");

    private int id;
    private String name;

    ShowAttendancesMenu(final int id, final String name) {
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
