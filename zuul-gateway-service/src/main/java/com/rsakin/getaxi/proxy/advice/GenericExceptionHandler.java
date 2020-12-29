package com.rsakin.getaxi.proxy.advice;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            JsonParseException.class,
            ValidationException.class
    })
    public ResponseEntity<Map<String, String>> exception(ValidationException ex) {
        return returnBadRequest(ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, String>> invalidRequestExceptionHandling(InvalidRequestException ex) {
        return returnBadRequest(ex.getMessage());
    }

    // If not found specific exception, use this
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception exception) {
        Map<String, String> response = prepareResponse(
                exception.getMessage(),
                "Please try again later or contact with IT of bla-bla",
                HttpStatus.INTERNAL_SERVER_ERROR.toString());
        log.info("There is an unknown issue.", exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> prepareResponse(String error, String solution, String status) {
        // You can define any other class for better visualization for response
        Map<String, String> response = new HashMap<>();
        response.put("Cause", error);
        response.put("Solution", solution);
        response.put("Status", status);
        return response;
    }

    private ResponseEntity<Map<String, String>> returnBadRequest(String message) {
        Map<String, String> response = prepareResponse(
                message,
                "Please enter a valid entity with proper constraints",
                HttpStatus.BAD_REQUEST.toString());
        log.info("Entity is not valid.", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}