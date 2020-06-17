package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShowVacantOnDateAction implements IAction {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = InputDataReader.getLocalDateInput(scanner, "Input date with format \"YYYY-MM-DD\"...");
        Room[] rooms = RoomController.getInstance().showVacantRoomsOnDate(date);
        List<Room> entities = Arrays.asList(rooms);
        Printer.show(entities);
    }
}
