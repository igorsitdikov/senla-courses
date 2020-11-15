package com.senla.bulletin_board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AbstractDto {

    private Long senderId;
    private Long recipientId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long dialogId;
    private String message;
    private LocalDateTime createdAt;
}
