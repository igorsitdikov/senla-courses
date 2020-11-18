package com.senla.bulletin_board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequestDto extends UserDto {

    private String password;

    public UserRequestDto(final String password) {
        this.password = password;
    }

    public UserRequestDto(final Long id,
                          final String firstName,
                          final String lastName,
                          final String email,
                          final String phone,
                          final String password) {
        super(id, firstName, lastName, email, phone);
        this.password = password;
    }
}
