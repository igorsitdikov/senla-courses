package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class PersistException extends BusinessLogicException {

    public PersistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
