package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class AddAttendanceToResidentAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final List<Attendance> attendances = AttendanceController.getInstance().showAttendances();
            Printer.show(attendances, "Choose attendance: ");

            Integer attendanceId = InputDataReader
                .getIntegerInput(scanner, "Input Attendance id...", attendances.size() - 1);

            final List<Resident> residents = ResidentController.getInstance().showResidents();
            Printer.show(residents, "Choose resident: ");

            Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

            ResidentController.getInstance()
                .addAttendanceToResident(residents.get(residentId - 1), attendances.get(attendanceId - 1));
        } catch (Exception e) {
            System.out.println(String.format("Failed to add an Attendance to Resident! Input valid parameters! %s", e));
        }
    }
}
