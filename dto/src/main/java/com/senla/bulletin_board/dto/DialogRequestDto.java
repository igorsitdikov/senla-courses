package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DialogRequestDto extends AbstractDto {

    private Long sellerId;
    private Long customerId;
    private Long bulletinId;
    private LocalDateTime createdAt;
}
