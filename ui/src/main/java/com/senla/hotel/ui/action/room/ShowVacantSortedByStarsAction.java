package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowVacantSortedByStarsAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowVacantSortedByStarsAction.class);

    private final RoomController roomController;

    public ShowVacantSortedByStarsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final List<Room> rooms = roomController.showVacantRooms(SortField.STARS);
            Printer.show(rooms);
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
