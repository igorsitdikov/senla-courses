package com.senla.hotel.dto;

import com.senla.hotel.enumerated.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto extends ADto{
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private UserRole role;
}
