package com.senla.bulletinboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentDto extends AbstractDto {

    private LocalDateTime payedAt;
    @NotNull(message = "user id is null")
    private Long userId;
    private BigDecimal payment;
}
