package com.senla.bulletin_board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignInDto extends AbstractDto {

    private String email;
    private String password;
}
