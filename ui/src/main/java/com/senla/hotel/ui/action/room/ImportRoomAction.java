package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportRoomAction implements Action {

    private static final Logger logger = LogManager.getLogger(ImportRoomAction.class);

    private RoomController roomController;

    public ImportRoomAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            roomController.importRooms();
        } catch (final PersistException e) {
            logger.error("Could not import rooms from csv {}", e.getMessage());
        }
    }
}
