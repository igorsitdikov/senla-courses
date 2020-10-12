package com.senla.hotel.exceptions;

import org.springframework.http.HttpStatus;

public class RoomStatusChangingException extends AbstractBusinessLogicException {

    public RoomStatusChangingException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
