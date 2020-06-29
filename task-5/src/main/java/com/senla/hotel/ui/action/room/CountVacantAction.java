package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.ui.interfaces.IAction;

public class CountVacantAction implements IAction {

    @Override
    public void execute() {
        final int vacantRooms = RoomController.getInstance().countVacantRooms();
        System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
    }
}
