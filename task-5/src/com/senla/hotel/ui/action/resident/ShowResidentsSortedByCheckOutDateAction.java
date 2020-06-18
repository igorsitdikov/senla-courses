package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;

public class ShowResidentsSortedByCheckOutDateAction implements IAction {

    @Override
    public void execute() {
        Resident[] residents = ResidentController.getInstance().showResidentsSortedByCheckOutDate();
        List<Resident> entities = Arrays.asList(residents);
        Printer.show(entities);
    }
}
