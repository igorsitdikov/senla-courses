package com.senla.bulletin_board.dto;

import lombok.Data;

@Data
public class Stars {

    private Long userId;
    private Long bulletinId;
    private Integer stars;
}
