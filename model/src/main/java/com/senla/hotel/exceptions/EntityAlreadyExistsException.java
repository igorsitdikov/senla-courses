package com.senla.hotel.exceptions;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }
}
