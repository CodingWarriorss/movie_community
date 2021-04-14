package com.codeworrisors.Movie_Community_Web.exception;

public class AlreadyUnLikeStateException extends IllegalStateException{
    public AlreadyUnLikeStateException() {
        super("이미 unlike 상태");
    }
}
