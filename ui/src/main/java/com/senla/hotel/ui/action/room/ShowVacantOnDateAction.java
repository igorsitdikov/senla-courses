package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ShowVacantOnDateAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowVacantOnDateAction.class);

    private final RoomController roomController;

    public ShowVacantOnDateAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        final Scanner scanner = new Scanner(System.in);
        final LocalDate date = InputDataReader.getLocalDateInput(scanner, "Input date with format \"YYYY-MM-DD\"...");
        try {
            final List<RoomDto> rooms = roomController.showVacantRoomsOnDate(date);
            Printer.show(rooms);
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
