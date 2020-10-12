package com.senla.hotel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PriceDto {

    @JsonProperty("price")
    private BigDecimal price;

    public PriceDto() {
    }

    public PriceDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
