package com.senla.bulletin_board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdResponseDto extends AbstractDto {

    private Long id;
}
