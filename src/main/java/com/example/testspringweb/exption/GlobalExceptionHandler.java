package com.example.testspringweb.exption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> handleInvalidException(InvalidException invalidException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(invalidException.getMessage());
        invalidException.printStackTrace();
        errorMessage.setDescription(webRequest.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(InvalidException invalidException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setMessage(invalidException.getMessage());
        invalidException.printStackTrace();
        errorMessage.setDescription(webRequest.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
