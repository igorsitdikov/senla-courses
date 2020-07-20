package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum ResidentMenuImpl implements MenuEnum {
    SHOW_RESIDENTS(1, "Show residents"),
    ADD_RESIDENT(2, "Add resident"),
    TOTAL_RESIDENTS(3, "Total residents"),
    ATTENDANCE_TO_RESIDENT(4, "Add attendance to resident");

    private int id;
    private String title;

    ResidentMenuImpl(final int id, final String title) {
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
