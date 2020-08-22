package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ImportRoomAction implements Action {
    private RoomController roomController;

    public ImportRoomAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            roomController.importRooms();
        } catch (final PersistException e) {
            System.err.printf("Could not import rooms from csv %s%n", e.getMessage());
        }
    }
}
