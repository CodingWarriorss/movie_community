package com.codeworrisors.Movie_Community_Web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationServiceException.class)
    protected ResponseEntity<ErrorResponse> handleAuthorizationServiceException(HttpServletRequest req, AuthorizationServiceException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.FORBIDDEN.toString())
                .message(e.getMessage())
                .path(req.getServletPath())
                .status(HttpStatus.FORBIDDEN).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected  ResponseEntity<ErrorResponse> handleNoSuchElementException( NoSuchElementException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    protected  ResponseEntity<ErrorResponse> handleAuthenticationServiceException( AuthenticationServiceException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.UNAUTHORIZED.toString())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
