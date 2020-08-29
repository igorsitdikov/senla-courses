package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsAction implements Action {

    private final ResidentController residentController;

    public ShowResidentsAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final List<Resident> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
