package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportAttendanceAction implements Action {

    private static final Logger logger = LogManager.getLogger(ImportAttendanceAction.class);

    private AttendanceController attendanceController;

    public ImportAttendanceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            attendanceController.importAttendances();
        } catch (final PersistException e) {
            logger.error("Could not import attendances from csv {}", e.getMessage());
        }
    }
}