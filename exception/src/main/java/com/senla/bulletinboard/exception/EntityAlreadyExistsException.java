package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BusinessLogicException {

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
