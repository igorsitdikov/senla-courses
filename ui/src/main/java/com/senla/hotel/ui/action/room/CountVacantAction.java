package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class CountVacantAction implements Action {
    private RoomController roomController;

    public CountVacantAction(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final int vacantRooms = roomController.countVacantRooms();
            System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
