package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class VoteAlreadyExistsException extends BusinessLogicException {

    public VoteAlreadyExistsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
