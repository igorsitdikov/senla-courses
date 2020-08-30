package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAttendancesSortedByNameAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowAttendancesSortedByNameAction.class);

    private final AttendanceController attendanceController;

    public ShowAttendancesSortedByNameAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            final List<Attendance> attendances = attendanceController.showAttendances(SortField.NAME);
            Printer.show(attendances);
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
