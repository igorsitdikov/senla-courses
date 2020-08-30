package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToStatus;

public class ChangeStatusAction implements Action {

    private static final Logger logger = LogManager.getLogger(ChangeStatusAction.class);

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
            logger.error("Failed to change Room's status! Input valid parameters! {}", e.getMessage());
        }
    }
}
