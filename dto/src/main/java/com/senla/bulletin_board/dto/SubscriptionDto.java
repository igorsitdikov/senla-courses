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
public class SubscriptionDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime subscribedAt;
    private Long userId;
    private Long tariffId;
}
