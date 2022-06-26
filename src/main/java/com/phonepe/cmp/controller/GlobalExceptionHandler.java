package com.phonepe.cmp.controller;

import com.phonepe.cmp.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity("NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> entityAlreadyExists(EntityAlreadyExistsException ex) {
        return buildResponseEntity("CONFLICT", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequest(BadRequestException ex) {
        return buildResponseEntity("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ExceptionResponse> invalidOperation(InvalidOperationException ex) {
        return buildResponseEntity("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    private ResponseEntity buildResponseEntity(String errorCode, String errorMessage, HttpStatus httpStatus) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, httpStatus);
    }
}
