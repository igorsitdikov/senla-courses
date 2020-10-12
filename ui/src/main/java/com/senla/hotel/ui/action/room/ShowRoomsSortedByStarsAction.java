package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowRoomsSortedByStarsAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowRoomsSortedByStarsAction.class);

    private final RoomController roomController;

    public ShowRoomsSortedByStarsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final List<RoomDto> rooms  = roomController.showAllRooms(SortField.STARS);
            Printer.show(rooms);
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
