package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ImportAttendanceAction implements Action {
    private AttendanceController attendanceController;

    public ImportAttendanceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            attendanceController.importAttendances();
        } catch (final PersistException e) {
            System.err.printf("Could not import attendances from csv %s%n", e.getMessage());
        }
    }
}
