package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.exception.BusinessLogicException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log
public class ExceptionControllerAdvice {

    @ExceptionHandler({BusinessLogicException.class})
    private ResponseEntity<Object> handleBusinessLogicExceptions(final BusinessLogicException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());

        ApiErrorDto err = new ApiErrorDto(
            LocalDateTime.now(),
            e.getHttpStatus(),
            "Business Logic Exception",
            details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
            .collect(Collectors.toList());

        ApiErrorDto err = new ApiErrorDto(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Validation Errors",
            details);

        return ResponseEntityBuilder.build(err);
    }

    public static class ResponseEntityBuilder {

        public static ResponseEntity<Object> build(ApiErrorDto apiError) {
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
}
