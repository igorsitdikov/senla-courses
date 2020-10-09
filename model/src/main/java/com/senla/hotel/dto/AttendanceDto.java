package com.senla.hotel.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class AttendanceDto extends ADto {

    private Long id;
    private BigDecimal price;
    private String name;

    public AttendanceDto() {
    }

    public AttendanceDto(final Long id, final BigDecimal price, final String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceDto that = (AttendanceDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(price, that.price) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name);
    }
}
