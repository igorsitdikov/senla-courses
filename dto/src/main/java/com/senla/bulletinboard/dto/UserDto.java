package com.senla.bulletinboard.dto;

import com.senla.bulletinboard.annotation.PhoneNumber;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

    protected Long id;
    @NotBlank(message = "first name is blank")
    protected String firstName;
    @NotBlank(message = "last name is blank")
    protected String lastName;
    @Email(message = "wrong email")
    @NotBlank(message = "email is blank")
    protected String email;
    @PhoneNumber(message = "phone number should be +375(25|29|33|44)xxxxxxx")
    protected String phone;
    protected AutoSubscribeStatus autoSubscribe;
    protected PremiumStatus premium;
    protected BigDecimal balance;
}
