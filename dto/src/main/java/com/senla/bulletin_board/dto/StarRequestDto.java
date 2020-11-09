package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StarRequestDto {

    private Long userId;
    private Long bulletinId;
    private Integer stars;
}
