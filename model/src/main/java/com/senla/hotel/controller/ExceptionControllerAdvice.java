package com.senla.hotel.controller;

import com.senla.hotel.exceptions.BusinessLogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LogManager.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(BusinessLogicException.class)
    private ResponseEntity<ErrorMessage> handleBadRequest(final BusinessLogicException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), e.getHttpStatus());
    }

    public static class ErrorMessage {

        private final String errorMessage;

        public ErrorMessage(final String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
