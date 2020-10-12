package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowResidentsAction implements Action {

    private static final Logger logger = LogManager.getLogger(ShowResidentsAction.class);

    private final ResidentController residentController;

    public ShowResidentsAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final List<ResidentDto> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents);
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }
}
