package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DialogRequestDto {

    private Long sellerId;
    private Long customerId;
    private Long bulletinId;
    private LocalDateTime createdAt;
}
