package com.example.schoolmanangement.exception;


import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentErrorHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Pair<Long, String>>
            handleApiException(ApiException error) {
        return new ResponseEntity<>(Pair.of(error.getBaseErrorResponse().getErrorCode(), error.getBaseErrorResponse().getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}
