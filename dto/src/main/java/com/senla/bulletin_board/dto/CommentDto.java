package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentDto extends AbstractDto {

    private UserDto author;
    private Long authorId;
    private String comment;
    private Long bulletinId;
    private LocalDateTime createdAt;
}
