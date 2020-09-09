package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountVacantAction implements Action {

    private static final Logger logger = LogManager.getLogger(CountVacantAction.class);

    private final RoomController roomController;

    public CountVacantAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final Long vacantRooms = roomController.countVacantRooms();
            Printer.show(String.format("Total vacant rooms: %s%n", vacantRooms));
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
