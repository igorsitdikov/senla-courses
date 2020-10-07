package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class AddAttendanceToResidentAction implements Action {

    private static final Logger logger = LogManager.getLogger(AddAttendanceToResidentAction.class);

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
            final List<AttendanceDto> attendances = attendanceController.showAttendances(SortField.DEFAULT);
            Printer.show(attendances, "attendance");
            Printer.show(attendances.size());
            Integer attendanceId = InputDataReader
                .getIntegerInput(scanner, "Input Attendance id...", attendances.size());

            final List<ResidentDto> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents, "resident");

            final Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size());

            residentController
                .addAttendanceToResident(new Long(residentId), new Long(attendanceId));
        } catch (final Exception e) {
            logger.error("Failed to add an Attendance to Resident! Input valid parameters! {}", e.getMessage());
        }
    }
}
