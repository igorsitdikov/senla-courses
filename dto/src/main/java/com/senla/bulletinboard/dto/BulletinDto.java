package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.bulletinboard.enumerated.BulletinStatus;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CommentDto> comments;
    private BulletinStatus status;

    public BulletinDto() {
    }

    public BulletinDto(final Long id,
                       final String title,
                       final BigDecimal price,
                       final LocalDateTime createdAt,
                       final UserDto author, final Long authorId) {
        super(id, title, price, createdAt, author, authorId);
    }

    public BulletinDto(final Long id,
                       final String title,
                       final BigDecimal price,
                       final LocalDateTime createdAt,
                       final UserDto author,
                       final Long authorId,
                       final String description,
                       final List<CommentDto> comments, final BulletinStatus status) {
        super(id, title, price, createdAt, author, authorId);
        this.description = description;
        this.comments = comments;
        this.status = status;
    }
}
