package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class BulletinIsClosedException extends BusinessLogicException {

    public BulletinIsClosedException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
