package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AbstractDto {

    @NotNull(message = "sender id is null")
    private Long senderId;
    @NotNull(message = "recipient id is null")
    private Long recipientId;
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "dialog id is null")
    private Long dialogId;
    private String message;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;
}
