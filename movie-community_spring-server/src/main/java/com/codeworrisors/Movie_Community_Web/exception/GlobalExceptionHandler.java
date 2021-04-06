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

    //권한 관련 Exception
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

    //인증 관련 Exception
    @ExceptionHandler(AuthenticationServiceException.class)
    protected  ResponseEntity<ErrorResponse> handleAuthenticationServiceException( AuthenticationServiceException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.UNAUTHORIZED.toString())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    //Resource Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    protected  ResponseEntity<ErrorResponse> handleResourceNotFoundException( ResourceNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //요구 사항에 맞지 않는 Request Exception
    @ExceptionHandler(BadRequestException.class)
    protected  ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(HttpStatus.BAD_REQUEST.toString())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
