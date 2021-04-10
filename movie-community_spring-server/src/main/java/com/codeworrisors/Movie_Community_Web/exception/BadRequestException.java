package com.codeworrisors.Movie_Community_Web.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
