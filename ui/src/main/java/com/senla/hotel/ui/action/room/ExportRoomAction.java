package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportRoomAction implements Action {

    private static final Logger logger = LogManager.getLogger(ExportRoomAction.class);

    private RoomController roomController;

    public ExportRoomAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            roomController.exportRooms();
        } catch (final PersistException e) {
            logger.error("Could not export rooms to csv {}", e.getMessage());
        }
    }
}