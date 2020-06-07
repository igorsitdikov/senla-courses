package com.senla.hotel.entity;

import java.math.BigDecimal;

public class Attendance extends AbstractEntity {
    private BigDecimal price;
    private String name;

    public Attendance(final BigDecimal price, final String name) {
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
        return "Attendance{" +
               "id=" + id +
               ", price=" + price +
               ", name='" + name + '\'' +
               '}';
    }
}
