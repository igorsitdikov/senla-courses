package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class SuchUserHasNoPermissionsException extends AbstractBusinessLogicException {
    public SuchUserHasNoPermissionsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
