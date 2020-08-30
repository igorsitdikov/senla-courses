package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportResidentAction implements Action {

    private static final Logger logger = LogManager.getLogger(ExportResidentAction.class);

    private ResidentController residentController;

    public ExportResidentAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            residentController.exportResidents();
        } catch (final PersistException e) {
            logger.error("Could not export residents to csv {}", e.getMessage());
        }
    }
}
