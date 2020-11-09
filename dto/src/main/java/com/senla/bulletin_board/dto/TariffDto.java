package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TariffDto {

    private Long id;
    private BigDecimal price;
    private Integer term;
    private String description;
}
