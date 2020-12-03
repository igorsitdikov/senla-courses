package com.senla.bulletinboard.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    BUSINESS_LOGIC("Business Logic Exception"),
    ERROR_OCCURRED("Error occurred"),
    VALIDATION("Validation Errors");

    private String message;
}

