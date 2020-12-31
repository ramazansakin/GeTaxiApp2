package com.rsakin.getaxi.proxy.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.rsakin.getaxi.proxy.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));

                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        Map<String, String> response = new HashMap<>();
        response.put("Cause", errors.toString());
        response.put("Solution", "Please enter a valid password");
        response.put("Status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            JsonParseException.class,
            ValidationException.class
    })
    public ResponseEntity<Map<String, String>> exception(ValidationException ex) {
        return returnBadRequest(ex.getMessage());
    }

    @ExceptionHandler({AuthenticationServiceException.class})
    public ResponseEntity<Map<String, String>> handleAuthenticationServiceException(AuthenticationServiceException ex) {
        Map<String, String> response = prepareResponse(
                ex.getMessage(),
                "Please register before authentication.",
                HttpStatus.UNAUTHORIZED.toString());
        log.info("Unauthenticated user - ", ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
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