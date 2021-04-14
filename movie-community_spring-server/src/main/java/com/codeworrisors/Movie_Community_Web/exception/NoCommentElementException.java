package com.codeworrisors.Movie_Community_Web.exception;

import java.util.NoSuchElementException;

public class NoCommentElementException extends NoSuchElementException {
    public NoCommentElementException() {
        super("존재하지 않는 댓글");
    }
}
