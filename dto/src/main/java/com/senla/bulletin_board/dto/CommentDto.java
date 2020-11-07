package com.senla.bulletin_board.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {

    private UserResponseDto author;
    private String comment;
    private LocalDate createdAt;
}
