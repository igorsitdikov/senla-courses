package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToStatus;

public class ChangeStatusAction implements Action {
    private final RoomController roomController;

    public ChangeStatusAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        final Scanner scanner = new Scanner(System.in);
        try {
            final Integer roomNumber = InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            final RoomStatus status = integerToStatus(
                    InputDataReader
                            .getIntegerInput(scanner,
                                    "Input the Room status, where\n " +
                                            "\t1 = VACANT, " +
                                            "\t2 = OCCUPIED, " +
                                            "\t3 = REPAIR...",
                                    RoomStatus.values().length));

            roomController.changeStatus(roomNumber, status);
        } catch (final Exception e) {
            System.err.printf("Failed to change Room's status! Input valid parameters! %s%n%n", e);
        }
    }
}
