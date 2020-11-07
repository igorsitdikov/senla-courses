package com.senla.bulletin_board.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TariffDto {

    private Long id;
    private BigDecimal price;
    private Integer term;
}
