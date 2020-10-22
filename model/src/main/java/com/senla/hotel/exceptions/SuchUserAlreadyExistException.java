package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class SuchUserAlreadyExistException extends AbstractBusinessLogicException {
    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
