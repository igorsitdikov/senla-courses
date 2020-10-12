package com.senla.hotel.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class CheckOutDto {

    private ResidentDto resident;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    public CheckOutDto() {
    }

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
