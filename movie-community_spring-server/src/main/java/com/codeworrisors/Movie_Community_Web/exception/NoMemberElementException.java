package com.codeworrisors.Movie_Community_Web.exception;

import java.util.NoSuchElementException;

public class NoMemberElementException extends NoSuchElementException {

    public NoMemberElementException () {
        super("존재하지 않는 Member");
    }
}
