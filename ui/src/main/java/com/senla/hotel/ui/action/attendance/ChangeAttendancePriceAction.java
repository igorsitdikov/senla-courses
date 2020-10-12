package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ChangeAttendancePriceAction implements Action {

    private static final Logger logger = LogManager.getLogger(ChangeAttendancePriceAction.class);

    private final AttendanceController attendanceController;

    public ChangeAttendancePriceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final List<AttendanceDto> attendances = attendanceController.showAttendances(SortField.DEFAULT);
            Printer.show(attendances);
            final Long id = InputDataReader.getLongInput(scanner, "Input the Attendance id...");
            final BigDecimal dailyPrice = BigDecimal.valueOf(InputDataReader
                    .getDoubleInput(scanner, "Input the Attendance daily price..."));
            attendanceController.changePrice(new Long(id), new PriceDto(dailyPrice));
        } catch (final Exception e) {
            logger.error("Failed to change price a Attendance! Input valid parameters! {}", e.getMessage());
        }
    }
}
