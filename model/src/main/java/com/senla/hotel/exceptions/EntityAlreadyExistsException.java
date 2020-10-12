package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends AbstractBusinessLogicException {

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
