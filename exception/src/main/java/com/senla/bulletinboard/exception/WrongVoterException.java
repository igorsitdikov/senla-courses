package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class WrongVoterException extends BusinessLogicException {

    public WrongVoterException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
