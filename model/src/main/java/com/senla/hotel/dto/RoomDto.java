package com.senla.hotel.dto;

import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class RoomDto extends ADto {

    private Long id;
    private Integer number;
    private Stars stars;
    private Accommodation accommodation;
    private BigDecimal price;
    private RoomStatus status;
    private List<RoomHistoryDto> historiesDto;

    public RoomDto() {
    }

    public RoomDto(final Long id,
                    final Integer number,
                    final Stars stars,
                    final Accommodation accommodation,
                    final BigDecimal price,
                    final RoomStatus status, final List<RoomHistoryDto> historiesDto) {
        this.id = id;
        this.number = number;
        this.stars = stars;
        this.accommodation = accommodation;
        this.price = price;
        this.status = status;
        this.historiesDto = historiesDto;
    }

    public Long getId() {
        return id;
    }

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

    public List<RoomHistoryDto> getHistoriesDto() {
        return historiesDto;
    }

    public void setHistoriesDto(final List<RoomHistoryDto> historiesDto) {
        this.historiesDto = historiesDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(id, roomDto.id) &&
                Objects.equals(number, roomDto.number) &&
                stars == roomDto.stars &&
                accommodation == roomDto.accommodation &&
                Objects.equals(price, roomDto.price) &&
                status == roomDto.status &&
                Objects.equals(historiesDto, roomDto.historiesDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, stars, accommodation, price, status, historiesDto);
    }
}
