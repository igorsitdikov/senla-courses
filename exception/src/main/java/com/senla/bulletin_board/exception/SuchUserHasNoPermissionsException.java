package com.senla.bulletin_board.exception;

import org.springframework.http.HttpStatus;

public class SuchUserHasNoPermissionsException extends BusinessLogicException {

    public SuchUserHasNoPermissionsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
