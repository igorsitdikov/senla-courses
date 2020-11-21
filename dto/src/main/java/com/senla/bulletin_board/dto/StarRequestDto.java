package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StarRequestDto extends AbstractDto {

    private Long userId;
    private Long bulletinId;
    private Integer stars;
}