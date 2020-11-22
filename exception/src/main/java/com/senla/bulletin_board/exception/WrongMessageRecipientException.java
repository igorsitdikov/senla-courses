package com.senla.bulletin_board.exception;

import org.springframework.http.HttpStatus;

public class WrongMessageRecipientException extends BusinessLogicException {

    public WrongMessageRecipientException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
