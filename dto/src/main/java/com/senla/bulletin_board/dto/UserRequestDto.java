package com.senla.bulletin_board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserRequestDto extends AbstractDto  {

    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String phone;
}
