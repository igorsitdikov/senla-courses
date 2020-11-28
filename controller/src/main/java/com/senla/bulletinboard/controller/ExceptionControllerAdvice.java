package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.exception.BusinessLogicException;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log
public class ExceptionControllerAdvice {

    @ExceptionHandler({BusinessLogicException.class})
    private ResponseEntity<ErrorMessage> handleBadRequest(final BusinessLogicException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), e.getHttpStatus());
    }

    @Data
    public static class ErrorMessage {

        private final String errorMessage;
    }
}
