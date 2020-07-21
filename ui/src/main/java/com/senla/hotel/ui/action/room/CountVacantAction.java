package com.senla.hotel.ui.action.room;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.ui.interfaces.Action;

public class CountVacantAction implements Action {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        final int vacantRooms = roomController.countVacantRooms();
        System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
    }
}
