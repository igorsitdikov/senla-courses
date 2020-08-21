package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowResidentsSortedByCheckOutDateAction implements Action {
    private final ResidentController residentController;

    public ShowResidentsSortedByCheckOutDateAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final List<Resident> residents = residentController.showResidents(SortField.CHECK_OUT_DATE);
            Printer.show(residents);
        } catch (final PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
