package com.senla.bulletinboard.dto;

import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
                           final AutoSubscribeStatus autoSubscribe,
                           final PremiumStatus premium,
                           final BigDecimal balance,
                           final String password) {
        super(id, firstName, lastName, email, phone, autoSubscribe, premium, balance);
        this.password = password;
    }
}
