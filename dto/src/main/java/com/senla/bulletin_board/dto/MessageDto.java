package com.senla.bulletin_board.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MessageDto {

    private Long senderId;
    private Long recipientId;
    private Long bulletinId;
    private String message;
    private LocalDate createdAt;
}
