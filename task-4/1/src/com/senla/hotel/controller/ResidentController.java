package com.senla.hotel.controller;

import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.interfaces.IResidentService;

public final class ResidentController {
    private static ResidentController residentController;
    private static IResidentService residentService;

    public ResidentController() {
        residentService = new ResidentService();
    }

    public static ResidentController getInstance() {
        if (residentController == null) {
            residentController = new ResidentController();
        }
        return residentController;
    }
}
