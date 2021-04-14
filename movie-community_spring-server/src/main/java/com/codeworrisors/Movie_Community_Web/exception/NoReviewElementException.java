package com.codeworrisors.Movie_Community_Web.exception;

import java.util.NoSuchElementException;

public class NoReviewElementException extends NoSuchElementException {

    public NoReviewElementException() {
        super("존재하지 않는 리뷰");
    }
}
