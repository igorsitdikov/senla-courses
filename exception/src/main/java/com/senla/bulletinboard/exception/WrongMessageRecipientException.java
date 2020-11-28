package com.senla.bulletinboard.exception;

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
