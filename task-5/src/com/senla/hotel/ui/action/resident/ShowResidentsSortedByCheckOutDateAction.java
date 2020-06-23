package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsSortedByCheckOutDateAction implements IAction {

    @Override
    public void execute() {
        List<Resident> residents = ResidentController.getInstance().showResidentsSortedByCheckOutDate();
        Printer.show(residents);
    }
}
