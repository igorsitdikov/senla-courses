package com.senla.bulletinboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordDto extends AbstractDto {

    @NotBlank(message = "password is blank")
    private String oldPassword;
    @Size(min = 6, message = "too short password")
    @NotBlank(message = "password is blank")
    private String newPassword;
    @Size(min = 6, message = "too short password")
    @NotBlank(message = "password is blank")
    private String confirmPassword;
}
