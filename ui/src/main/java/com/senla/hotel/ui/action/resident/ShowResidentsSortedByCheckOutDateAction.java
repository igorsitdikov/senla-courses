package com.senla.hotel.ui.action.resident;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

@MenuItem
public class ShowResidentsSortedByCheckOutDateAction implements IAction {
    @Autowired
    private ResidentController residentController;

    @Override
    public void execute() {
        List<Resident> residents = residentController.showResidentsSortedByCheckOutDate();
        Printer.show(residents);
    }
}
