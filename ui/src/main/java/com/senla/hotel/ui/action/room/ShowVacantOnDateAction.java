package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ShowVacantOnDateAction implements Action {
    private RoomController roomController;

    public ShowVacantOnDateAction(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = InputDataReader.getLocalDateInput(scanner, "Input date with format \"YYYY-MM-DD\"...");
        try {
            List<Room> rooms = roomController.showVacantRoomsOnDate(date);
            Printer.show(rooms);
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
