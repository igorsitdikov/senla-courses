package com.senla.bulletin_board.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
}
