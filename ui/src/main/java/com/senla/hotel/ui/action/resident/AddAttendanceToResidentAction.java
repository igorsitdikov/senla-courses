package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class AddAttendanceToResidentAction implements Action {
    private final AttendanceController attendanceController;
    private final ResidentController residentController;

    public AddAttendanceToResidentAction(final AttendanceController attendanceController, final ResidentController residentController) {
        this.attendanceController = attendanceController;
        this.residentController = residentController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final List<Attendance> attendances = attendanceController.showAttendances(SortField.DEFAULT);
            Printer.show(attendances, "attendance");

            final Integer attendanceId = InputDataReader
                .getIntegerInput(scanner, "Input Attendance id...", attendances.size());

            final List<Resident> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents, "resident");

            final Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size());

            residentController
                .addAttendanceToResident(residents.get(residentId - 1), attendances.get(attendanceId - 1));
        } catch (final Exception e) {
            System.err.printf("Failed to add an Attendance to Resident! Input valid parameters! %s%n%n", e);
        }
    }
}
