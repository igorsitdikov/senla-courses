package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class CountVacantAction implements Action {

    private final RoomController roomController;

    public CountVacantAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final int vacantRooms = roomController.countVacantRooms();
            System.out.printf("Total vacant rooms: %d%n%n", vacantRooms);
        } catch (final PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
