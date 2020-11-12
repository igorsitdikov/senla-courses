package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TariffDto extends AbstractDto {

    private Long id;
    private BigDecimal price;
    private Integer term;
    private String description;
}
