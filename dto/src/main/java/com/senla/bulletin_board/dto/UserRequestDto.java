package com.senla.bulletin_board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String phone;
}
