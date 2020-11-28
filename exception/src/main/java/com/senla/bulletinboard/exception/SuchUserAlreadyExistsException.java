package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class SuchUserAlreadyExistsException extends BusinessLogicException {

    public SuchUserAlreadyExistsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
