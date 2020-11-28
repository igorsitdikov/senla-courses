package com.senla.bulletinboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordDto extends AbstractDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
