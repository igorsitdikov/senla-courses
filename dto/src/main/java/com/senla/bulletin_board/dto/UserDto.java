package com.senla.bulletin_board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
