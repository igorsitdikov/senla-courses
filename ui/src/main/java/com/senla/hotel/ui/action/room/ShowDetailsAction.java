package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ShowDetailsAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowDetailsAction.class);

    private final RoomController roomController;

    public ShowDetailsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final Integer roomNumber = InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            final RoomDto room = roomController.showRoomDetails(roomNumber);
            Printer.show(room);
        } catch (final Exception e) {
            logger.error("Failed to add a Room! Input valid parameters! {}", e.getMessage());
        }
    }
}
