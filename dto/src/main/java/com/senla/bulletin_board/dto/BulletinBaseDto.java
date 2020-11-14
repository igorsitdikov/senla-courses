package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinBaseDto extends AbstractDto {

    protected Long id;
    protected String title;
    protected BigDecimal price;
    protected LocalDateTime createdAt;
    protected UserDto author;
}
