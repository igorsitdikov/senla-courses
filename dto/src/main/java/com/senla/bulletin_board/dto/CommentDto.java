package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {

    private UserDto author;
    private String comment;
    private LocalDateTime createdAt;
}
