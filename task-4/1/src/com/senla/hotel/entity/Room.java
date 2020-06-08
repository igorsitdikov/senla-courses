package com.senla.hotel.entity;

import com.senla.hotel.entity.type.Accommodation;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.entity.type.Stars;

import java.math.BigDecimal;

public class Room extends AbstractEntity {
    private Integer number;
    private Stars stars;
    private Accommodation accommodation;
    private BigDecimal price;
    private RoomStatus status;
    private RoomHistory[] histories;

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
        histories = new RoomHistory[0];
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

    public RoomHistory[] getHistories() {
        return histories;
    }

    public void setHistories(final RoomHistory[] histories) {
        this.histories = histories;
    }

    @Override
    public String toString() {
        return "Room{" +
               "number=" + number +
               ", stars=" + stars +
               ", accommodation=" + accommodation +
               ", price=" + price +
               ", status=" + status +
               ", id=" + id +
               '}';
    }
}
