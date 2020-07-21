package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.Scanner;

public class ShowDetailsAction implements Action {
    private RoomController roomController;

    public ShowDetailsAction(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            Integer roomNumber = InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            Room room = roomController.showRoomDetails(roomNumber);
            Printer.show(room);
        } catch (Exception e) {
            System.err.println(String.format("Failed to add a Room! Input valid parameters! %s", e));
        }

    }
}
