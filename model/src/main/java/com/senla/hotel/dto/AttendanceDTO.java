package com.senla.hotel.dto;

import java.math.BigDecimal;

public class AttendanceDTO extends ADataTransferObject {
    private BigDecimal price;
    private String name;

    public AttendanceDTO() {
    }

    public AttendanceDTO(final BigDecimal price, final String name) {
        this.price = price;
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Attendance \"%s\" with price %.2f per day.", name, price);
    }
}
