package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends AbstractBusinessLogicException {

    public InvalidArgumentException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
