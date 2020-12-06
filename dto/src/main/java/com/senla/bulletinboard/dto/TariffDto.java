package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TariffDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private BigDecimal price;
    private Integer term;
    private String description;
}
