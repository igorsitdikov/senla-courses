package com.senla.bulletinboard.exception;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends BusinessLogicException {

    public InsufficientFundsException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PAYMENT_REQUIRED;
    }
}
