package com.senla.bulletin_board.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BulletinDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private LocalDate createdAt;
    private UserResponseDto author;
}
