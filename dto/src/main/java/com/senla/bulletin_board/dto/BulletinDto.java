package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BulletinDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private UserDto author;
}
