package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowAttendancesAction implements IAction {

    @Override
    public void execute() {
        List<Attendance> attendances = AttendanceController.getInstance().showAttendances();
        Printer.show(attendances);
    }
}
