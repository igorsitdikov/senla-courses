package com.senla.bulletin_board.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
}
