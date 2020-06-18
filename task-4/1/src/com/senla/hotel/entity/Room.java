package com.senla.hotel.entity;

import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Room extends AEntity {
    private Integer number;
    private Stars stars;
    private Accommodation accommodation;
    private BigDecimal price;
    private RoomStatus status;
    private List<RoomHistory> histories;

    public Room(final Integer number,
                final Stars stars,
                final Accommodation accommodation,
                final BigDecimal price,
                final RoomStatus status) {
        this.number = number;
        this.stars = stars;
        this.accommodation = accommodation;
        this.price = price;
        this.status = status;
        histories = new ArrayList<>();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Stars getStars() {
        return stars;
    }

    public void setStars(final Stars stars) {
        this.stars = stars;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(final Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(final RoomStatus status) {
        this.status = status;
    }

    public List<RoomHistory> getHistories() {
        return histories;
    }

    public void setHistories(final List<RoomHistory> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (!number.equals(other.getNumber())) {
            return false;
        }
        if (!stars.equals(other.getStars())) {
            return false;
        }
        if (accommodation.equals(other.getAccommodation())) {
            return false;
        }
        if (!price.equals(other.getPrice())) {
            return false;
        }
        if (!status.equals(other.getStatus())) {
            return false;
        }
        return histories.size() == (other.getHistories().size());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room №");
        sb.append(number);
        sb.append(" can hold up to ");
        sb.append(StringUtils.accommodationToString(accommodation));
        sb.append(". Price per day: ");
        sb.append(String.format("%.2f.", price));
        sb.append(" Room is ");
        sb.append(StringUtils.statusToString(status));
        sb.append(".");
        return sb.toString();
    }
}
