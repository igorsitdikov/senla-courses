package com.senla.hotel.ui.action.resident;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsSortedByCheckOutDateAction implements Action {
    @Autowired
    private ResidentController residentController;

    @Override
    public void execute() {
        List<Resident> residents = residentController.showResidentsSortedByCheckOutDate();
        Printer.show(residents);
    }
}
