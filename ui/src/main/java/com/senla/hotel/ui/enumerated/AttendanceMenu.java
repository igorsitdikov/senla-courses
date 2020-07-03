package com.senla.hotel.ui.enumerated;

public enum AttendanceMenu implements IMenuEnum {
    SHOW_ATTENDANCES(1, "Show attendances"),
    ADD_ATTENDANCE(2, "Create attendance"),
    CHANGE_ATTENDANCE_PRICE(3, "Change attendance price");

    private int id;
    private String name;

    AttendanceMenu(final int id, final String name) {
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
