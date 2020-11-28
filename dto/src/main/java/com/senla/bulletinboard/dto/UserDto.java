package com.senla.bulletinboard.dto;

import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phone;
    protected AutoSubscribeStatus autoSubscribe;
    protected PremiumStatus premium;
    protected BigDecimal balance;
}