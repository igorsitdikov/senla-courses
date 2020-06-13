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
        StringBuilder sb = new StringBuilder();
        sb.append("Room №");
        sb.append(number);
        sb.append(" can hold up to ");
        switch (accommodation) {
            case SGL_CHD:
                sb.append("single resident with 1 kid.");
                break;
            case SGL_2_CHD:
                sb.append("single resident with 2 kids.");
                break;
            case DBL:
                sb.append("double residents.");
                break;
            case DBL_EXB:
                sb.append("double residents with extra bad.");
                break;
            case DBL_CHD:
                sb.append("double residents with 1 kid.");
                break;
            case DBL_EXB_CHD:
                sb.append("double residents with 1 kid and extra bad.");
                break;
            case TRPL:
                sb.append("triple residents.");
                break;
            case TRPL_CHD:
                sb.append("triple residents with 1 kid.");
                break;
            case TRPL_2_CHLD:
                sb.append("triple residents with 2 kids.");
                break;
            default:
                sb.append("single resident.");
        }

        sb.append(" Price per day: ");
        sb.append(String.format("%.2f.", price));
        sb.append(" Room is ");
        switch (status) {
            case OCCUPIED:
                sb.append("occupied.");
                break;
            case REPAIR:
                sb.append("being repaired.");
                break;
            default:
                sb.append("vacant.");
        }

        return sb.toString();
    }
}
