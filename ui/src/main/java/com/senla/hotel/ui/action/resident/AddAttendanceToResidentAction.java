package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class AddAttendanceToResidentAction implements Action {
    private AttendanceController attendanceController;
    private ResidentController residentController;

    public AddAttendanceToResidentAction(AttendanceController attendanceController, ResidentController residentController) {
        this.attendanceController = attendanceController;
        this.residentController = residentController;
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final List<Attendance> attendances = attendanceController.showAttendances();
            Printer.show(attendances, "attendance");

            Integer attendanceId = InputDataReader
                .getIntegerInput(scanner, "Input Attendance id...", attendances.size());

            final List<Resident> residents = residentController.showResidents();
            Printer.show(residents, "resident");

            Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size());

            residentController
                .addAttendanceToResident(residents.get(residentId - 1), attendances.get(attendanceId - 1));
        } catch (Exception e) {
            System.err.println(String.format("Failed to add an Attendance to Resident! Input valid parameters! %s", e));
        }
    }
}
