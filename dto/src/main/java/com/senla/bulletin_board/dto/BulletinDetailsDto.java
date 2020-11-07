package com.senla.bulletin_board.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BulletinDetailsDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private UserResponseDto author;
    private String description;
    private List<CommentDto> comments;
}
