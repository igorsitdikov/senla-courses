package com.senla.bulletin_board.dto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String phone;
}
