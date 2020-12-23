package com.senla.bulletinboard.utils.validator;

import com.senla.bulletinboard.utils.validator.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(final PhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String phoneNumber, final ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return false;
        }

        return phoneNumber.matches("\\+375(29|25|33|44)\\d{7}");
    }
}
