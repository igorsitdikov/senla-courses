package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinDto extends AbstractDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private UserDto author;
}
