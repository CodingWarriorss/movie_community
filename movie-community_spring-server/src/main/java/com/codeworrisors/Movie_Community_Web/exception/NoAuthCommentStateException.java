package com.codeworrisors.Movie_Community_Web.exception;

import javax.xml.stream.events.Comment;

public class NoAuthCommentStateException extends IllegalStateException{
    public NoAuthCommentStateException() {
        super("권한 없는 댓글");
    }
}
