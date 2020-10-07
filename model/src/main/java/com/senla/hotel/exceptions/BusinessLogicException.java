package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BusinessLogicException extends Exception {

    protected HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BusinessLogicException(final String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
