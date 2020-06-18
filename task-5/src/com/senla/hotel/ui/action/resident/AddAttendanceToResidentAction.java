package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class AddAttendanceToResidentAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final Attendance[] attendances = AttendanceController.getInstance().showAttendances();
            List<Attendance> attendanceList = Arrays.asList(attendances);
            System.out.println("Choose attendance: ");
            IntStream.range(0, attendanceList.size())
                    .forEach(index -> String.format("%d. %s", (index + 1), attendanceList.get(index)));
            Integer attendanceId = InputDataReader
                    .getIntegerInput(scanner, "Input Attendance id...", attendances.length - 1);

            final Resident[] residents = ResidentController.getInstance().showResidents();
            List<Resident> residentsList = Arrays.asList(residents);
            System.out.println("Choose resident: ");
            IntStream.range(0, residentsList.size())
                    .forEach(index -> String.format("%d. %s", (index + 1), residentsList.get(index)));
            Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.length - 1);

            ResidentController.getInstance()
                    .addAttendanceToResident(residentsList.get(residentId - 1), attendanceList.get(attendanceId - 1));
        } catch (Exception e) {
            System.out.println(String.format("Failed to add an Attendance to Resident! Input valid parameters! %s", e));
        }
    }
}
