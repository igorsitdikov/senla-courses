package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ExportRoomAction implements Action {

    private RoomController roomController;

    public ExportRoomAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            roomController.exportRooms();
        } catch (final PersistException e) {
            System.err.printf("Could not export rooms to csv %s%n", e.getMessage());
        }
    }
}
