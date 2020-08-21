package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsAction implements Action {
    private ResidentController residentController;

    public ShowResidentsAction(ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            List<Resident> residents = residentController.showResidents();
            Printer.show(residents);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
