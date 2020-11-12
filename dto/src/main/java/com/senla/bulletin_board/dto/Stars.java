package com.senla.bulletin_board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Stars extends AbstractDto {

    private Long userId;
    private Long bulletinId;
    private Integer stars;
}
