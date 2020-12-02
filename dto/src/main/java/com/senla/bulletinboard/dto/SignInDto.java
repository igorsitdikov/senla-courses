package com.senla.bulletinboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignInDto extends AbstractDto {

    @Email(message = "wrong email")
    @NotBlank(message = "email is blank")
    private String email;
    @Size(min = 6, message = "too short password")
    @NotBlank(message = "password is blank")
    private String password;
}
