package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum ShowAttendancesMenuImpl implements MenuEnum {
    ALL_ATTENDANCES(1, "Show all attendances"),
    SORT_ATTENDANCES_BY_NAME(2, "Show attendances sorted by name"),
    SORT_ATTENDANCES_BY_PRICE(3, "Show attendances sorted by price");

    private int id;
    private String title;

    ShowAttendancesMenuImpl(final int id, final String title) {
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
