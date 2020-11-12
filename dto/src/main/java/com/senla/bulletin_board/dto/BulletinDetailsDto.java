package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinDetailsDto extends AbstractDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private LocalDateTime createAt;
    private UserDto author;
    private String description;
    private List<CommentDto> comments;
}
