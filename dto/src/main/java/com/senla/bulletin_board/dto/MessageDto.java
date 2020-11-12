package com.senla.bulletin_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AbstractDto {

    private Long senderId;
    private Long recipientId;
    private Long dialogId;
    private String message;
    private LocalDateTime createdAt;
}
