package com.senla.bulletin_board.dto;

import com.senla.bulletin_board.enumerated.PremiumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentDto extends AbstractDto {

    private LocalDateTime payedAt;
    private Long userId;
    private BigDecimal payment;
}
