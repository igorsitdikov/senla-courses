package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddAttendanceAction implements Action {

    private final AttendanceController attendanceController;

    public AddAttendanceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final String name = InputDataReader.getStringInput(scanner, "Input the Attendance name...");
            final BigDecimal dailyPrice = BigDecimal.valueOf(InputDataReader
                    .getDoubleInput(scanner, "Input the Attendance daily price..."));
            attendanceController.createAttendance(new Attendance(dailyPrice, name));
        } catch (final Exception e) {
            System.err.printf("Failed to add a Attendance! Input valid parameters! %s%n%n", e);
        }
    }
}
