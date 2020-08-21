package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.Scanner;

public class ShowDetailsAction implements Action {
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
            final Room room = roomController.showRoomDetails(roomNumber);
            Printer.show(room);
        } catch (final Exception e) {
            System.err.printf("Failed to add a Room! Input valid parameters! %s%n%n", e);
        }

    }
}
