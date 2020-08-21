package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsSortedByNameAction implements Action {
    private ResidentController residentController;

    public ShowResidentsSortedByNameAction(ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            List<Resident> residents = residentController.showResidentsSortedByName();
            Printer.show(residents);
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
