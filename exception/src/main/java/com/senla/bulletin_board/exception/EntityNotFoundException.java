package com.senla.bulletin_board.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessLogicException {

    public EntityNotFoundException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
