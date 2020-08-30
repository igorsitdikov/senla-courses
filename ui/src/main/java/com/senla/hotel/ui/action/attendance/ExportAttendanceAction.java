package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportAttendanceAction implements Action {

    private static final Logger logger = LogManager.getLogger(ExportAttendanceAction.class);

    private AttendanceController attendanceController;

    public ExportAttendanceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            attendanceController.exportAttendances();
        } catch (final PersistException e) {
            logger.error("Could not export attendances to csv {}", e.getMessage());
        }
    }
}
