package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class ShowLastResidentsAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowLastResidentsAction.class);

    private final RoomController roomController;

    public ShowLastResidentsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        final Scanner scanner = new Scanner(System.in);
        try {
            final List<RoomDto> rooms = roomController.showAllRooms(SortField.DEFAULT);
            Printer.show(rooms, "room");

            final Integer roomId = InputDataReader
                .getIntegerInput(scanner, "Input Room id...", rooms.size() - 1);
            final Integer number = InputDataReader
                .getIntegerInput(scanner, "Input number of last...", Integer.MAX_VALUE);

            final List<ResidentDto> residents = roomController.showLastResidents(new Long(roomId), number);
            Printer.show(residents);
        } catch (final Exception e) {
            logger.error("Failed to show Resident in Room! Input valid parameters! {}", e.getMessage());
        }
    }
}
