package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.utils.validator.annotation.PhoneNumber;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected UserRole role;
    protected AutoSubscribeStatus autoSubscribe;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected PremiumStatus premium;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected BigDecimal balance;
}
