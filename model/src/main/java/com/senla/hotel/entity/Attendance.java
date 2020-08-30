package com.senla.hotel.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Attendance extends AEntity {

    private BigDecimal price;
    private String name;

    public Attendance() {
    }

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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Attendance that = (Attendance) o;
        return Objects.equals(price, that.price) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name);
    }

    @Override
    public String toString() {
        return String.format("Attendance \"%s\" with price %.2f per day. %d", name, price, id);
    }
}
