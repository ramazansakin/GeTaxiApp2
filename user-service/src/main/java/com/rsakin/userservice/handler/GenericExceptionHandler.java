package com.rsakin.userservice.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.rsakin.userservice.exception.InvalidRequestException;
import com.rsakin.userservice.exception.NotFoundException;
import com.rsakin.userservice.exception.NotValidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            JsonParseException.class,
            InvalidRequestException.class,
            ValidationException.class,
            NotValidPasswordException.class
    })
    public ResponseEntity<Map<String, String>> exception(ValidationException ex) {
        Map<String, String> response = prepareResponse(
                ex.getMessage(),
                "Please enter a valid entity with proper constraints",
                HttpStatus.BAD_REQUEST.toString());
        log.info("Entity is not valid.", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> exception(NotFoundException ex) {
        Map<String, String> response = prepareResponse(
                ex.getMessage(),
                "Please send a valid entity",
                HttpStatus.NOT_FOUND.toString());
        log.info("Entity not found.", ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Generic validation error handler method
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );
        Map<String, String> response = prepareResponse(
                (errorsMap.isEmpty() ? ex.getMessage() : errorsMap.toString()),
                "Please enter a valid entity with proper constraints",
                HttpStatus.BAD_REQUEST.toString());
        log.info("Entity is not valid.", ex);
        return new ResponseEntity<>(response, status);
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

}