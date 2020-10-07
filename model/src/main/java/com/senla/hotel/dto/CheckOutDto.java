package com.senla.hotel.dto;

import java.time.LocalDate;

public class CheckOutDto {

    private ResidentDto resident;
    private LocalDate date;

    public CheckOutDto(ResidentDto resident, LocalDate date) {
        this.resident = resident;
        this.date = date;
    }

    public ResidentDto getResident() {
        return resident;
    }

    public void setResident(ResidentDto resident) {
        this.resident = resident;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
