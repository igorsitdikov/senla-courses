package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DialogDto extends AbstractDto {

    private Long id;
    private String title;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long sellerId;
    private Long customerId;
    private Long bulletinId;
    private UserDto user;
    private BulletinDto bulletin;
    private LocalDateTime createdAt;
}
