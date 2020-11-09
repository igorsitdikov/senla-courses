package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BulletinDetailsDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private LocalDateTime createAt;
    private UserDto author;
    private String description;
    private List<CommentDto> comments;
}
