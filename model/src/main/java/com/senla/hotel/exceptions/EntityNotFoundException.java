package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends AbstractBusinessLogicException {

    public EntityNotFoundException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
