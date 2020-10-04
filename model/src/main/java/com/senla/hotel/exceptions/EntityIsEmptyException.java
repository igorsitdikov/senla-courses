package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class EntityIsEmptyException extends BusinessLogicException {

    public EntityIsEmptyException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NO_CONTENT;
    }
}
