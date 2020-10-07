package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowResidentsSortedByCheckOutDateAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowResidentsSortedByCheckOutDateAction.class);

    private final ResidentController residentController;

    public ShowResidentsSortedByCheckOutDateAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final List<ResidentDto> residents = residentController.showResidents(SortField.CHECK_OUT_DATE);
            Printer.show(residents);
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
