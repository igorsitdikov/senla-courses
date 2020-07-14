package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddAttendanceAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            String name = InputDataReader.getStringInput(scanner);
            BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
            AttendanceController.getInstance().createAttendance(new Attendance(dailyPrice, name));
        } catch (Exception e) {
            System.err.println(String.format("Failed to add a Attendance! Input valid parameters! %s", e));
        }
    }
}
