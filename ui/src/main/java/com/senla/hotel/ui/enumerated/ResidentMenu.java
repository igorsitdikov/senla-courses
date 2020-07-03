package com.senla.hotel.ui.enumerated;

public enum ResidentMenu implements IMenuEnum {
    SHOW_RESIDENTS(1, "Show residents"),
    ADD_RESIDENT(2, "Add resident"),
    TOTAL_RESIDENTS(3, "Total residents"),
    ATTENDANCE_TO_RESIDENT(4, "Add attendance to resident");

    private int id;
    private String name;

    ResidentMenu(final int id, final String name) {
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
