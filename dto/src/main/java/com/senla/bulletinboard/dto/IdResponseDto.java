package com.senla.bulletinboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdResponseDto extends AbstractDto {

    private Long id;
}
