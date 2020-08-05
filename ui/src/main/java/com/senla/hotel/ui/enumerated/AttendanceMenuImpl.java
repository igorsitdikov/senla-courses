package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum AttendanceMenuImpl implements MenuEnum {
    SHOW_ATTENDANCES(1, "Show attendances"),
    ADD_ATTENDANCE(2, "Create attendance"),
    CHANGE_ATTENDANCE_PRICE(3, "Change attendance price");

    private int id;
    private String title;

    AttendanceMenuImpl(final int id, final String title) {
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