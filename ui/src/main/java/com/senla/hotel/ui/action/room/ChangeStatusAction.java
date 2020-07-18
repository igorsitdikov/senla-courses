package com.senla.hotel.ui.action.room;

import com.senla.anntotaion.Autowired;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToStatus;

public class ChangeStatusAction implements IAction {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        try {
            Integer roomNumber =
                InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            RoomStatus status = integerToStatus(
                InputDataReader
                    .getIntegerInput(scanner,
                                     "Input the Room status, where\n " +
                                     "\t1 = VACANT, " +
                                     "\t2 = OCCUPIED, " +
                                     "\t3 = REPAIR...",
                                     RoomStatus.values().length));

            roomController.changeStatus(roomNumber, status);
        } catch (Exception e) {
            System.err.println(String.format("Failed to change Room's status! Input valid parameters! %s", e));
        }
    }
}
