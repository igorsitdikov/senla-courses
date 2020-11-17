package com.senla.bulletin_board.exception;

import org.springframework.http.HttpStatus;

public class SuchUserAlreadyExistException extends BusinessLogicException {

    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
