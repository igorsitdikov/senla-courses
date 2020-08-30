package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class ChangeAttendancePriceAction implements Action {

    private final AttendanceController attendanceController;

    public ChangeAttendancePriceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final Long id = InputDataReader.getLongInput(scanner);
            final BigDecimal dailyPrice = BigDecimal.valueOf(InputDataReader
                    .getDoubleInput(scanner, "Input the Attendance daily price..."));
            attendanceController.changePrice(id, dailyPrice);
        } catch (final Exception e) {
            System.err.printf("Failed to change price a Attendance! Input valid parameters! %s%n%n", e);
        }
    }
}
