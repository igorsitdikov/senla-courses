package com.senla.bulletin_board.exception;

import org.springframework.http.HttpStatus;

public class WrongRecipientException extends BusinessLogicException {

    public WrongRecipientException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
