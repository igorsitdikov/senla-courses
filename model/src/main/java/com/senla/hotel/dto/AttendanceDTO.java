package com.senla.hotel.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AttendanceDTO {

    private Long id;
    private BigDecimal price;
    private String name;
    private List<RoomHistoryDTO> histories;

    public AttendanceDTO() {
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

    public List<RoomHistoryDTO> getHistories() {
        return histories;
    }

    public void setHistories(List<RoomHistoryDTO> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceDTO that = (AttendanceDTO) o;
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
