package com.senla.bulletin_board.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BulletinDto extends BulletinBaseDto {

    private String description;
    private List<CommentDto> comments;

    public BulletinDto() {
    }

    public BulletinDto(final Long id,
                       final String title,
                       final BigDecimal price,
                       final LocalDateTime createdAt,
                       final UserDto author) {
        super(id, title, price, createdAt, author);
    }

    public BulletinDto(final Long id,
                       final String title,
                       final BigDecimal price,
                       final LocalDateTime createdAt,
                       final UserDto author,
                       final String description,
                       final List<CommentDto> comments) {
        super(id, title, price, createdAt, author);
        this.description = description;
        this.comments = comments;
    }
}
