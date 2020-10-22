package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends AbstractBusinessLogicException {
    public NoSuchUserException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
