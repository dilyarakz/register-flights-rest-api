package com.assessment.flights.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/***
 * Custom exception handler
 */
@ControllerAdvice
@RestController
public class CustomIllegalArgumentExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleCustomIlligalArgumentException(
            CustomIllegalArgumentException e){

        CustomIllegalArgumentExceptionResponse exceptionResponse =
                new CustomIllegalArgumentExceptionResponse("failed",
                        e.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
}
