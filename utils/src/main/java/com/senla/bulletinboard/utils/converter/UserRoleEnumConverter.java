package com.senla.bulletinboard.utils.converter;

import com.senla.bulletinboard.enumerated.UserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRoleEnumConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(final String s) {
        try {
            return UserRole.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return UserRole.USER;
        }
    }
}
