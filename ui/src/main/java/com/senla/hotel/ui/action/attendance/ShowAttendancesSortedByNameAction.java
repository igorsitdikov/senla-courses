package com.senla.hotel.ui.action.attendance;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

@MenuItem
public class ShowAttendancesSortedByNameAction implements IAction {
    @Autowired
    private AttendanceController attendanceController;

    @Override
    public void execute() {
        List<Attendance> attendances = attendanceController.showAttendancesSortedByName();
        Printer.show(attendances);
    }
}
