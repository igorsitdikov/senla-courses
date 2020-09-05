package com.senla.hotel.entity;

import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "number")
    private Integer number;
    @Enumerated(EnumType.STRING)
    @Column(name = "stars")
    private Stars stars;
    @Enumerated(EnumType.STRING)
    @Column(name = "accommodation")
    private Accommodation accommodation;
    @Column(name = "price")
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status;
    @OneToMany(mappedBy = "room")
    private List<RoomHistory> histories;

    public Room() {
    }

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Room room = (Room) o;
        return Objects.equals(number, room.number) &&
               stars == room.stars &&
               accommodation == room.accommodation &&
               Objects.equals(price, room.price) &&
               status == room.status &&
               Objects.equals(histories, room.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, stars, accommodation, price, status, histories);
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
