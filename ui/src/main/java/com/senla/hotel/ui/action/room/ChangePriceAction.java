package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Scanner;

public class ChangePriceAction implements Action {

    private static final Logger logger = LogManager.getLogger(ChangePriceAction.class);

    private final RoomController roomController;

    public ChangePriceAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        final Scanner scanner = new Scanner(System.in);
        final Integer roomNumber = InputDataReader
                .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
        final BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input new Room daily price..."));

        try {
            roomController.changePrice(roomNumber, dailyPrice);
        } catch (final EntityNotFoundException | PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
