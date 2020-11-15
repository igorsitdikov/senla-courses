package com.senla.bulletin_board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DialogDto extends AbstractDto {

    private Long id;
    private String title;
    @Getter(onMethod = @__( @JsonIgnore))
    @Setter
    private Long sellerId;
    private Long customerId;
    private Long bulletinId;
    private UserDto user;
    private BulletinDto bulletin;
    private LocalDateTime createdAt;
}
