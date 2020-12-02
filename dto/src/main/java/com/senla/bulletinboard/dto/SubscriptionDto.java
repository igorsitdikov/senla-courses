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
public class SubscriptionDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime subscribedAt;
    @NotNull(message = "user id is null")
    private Long userId;
    @NotNull(message = "tariff id is null")
    private Long tariffId;
}
