package com.senla.bulletin_board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {

    @JsonProperty("price_gte")
    private BigDecimal priceGte;
    @JsonProperty("price_lte")
    private BigDecimal priceLte;
}
