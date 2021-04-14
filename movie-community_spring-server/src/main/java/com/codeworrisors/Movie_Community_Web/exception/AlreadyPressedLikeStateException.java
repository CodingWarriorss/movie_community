package com.codeworrisors.Movie_Community_Web.exception;

import com.codeworrisors.Movie_Community_Web.model.Likes;

public class AlreadyPressedLikeStateException extends IllegalStateException{
    public AlreadyPressedLikeStateException(Likes likes) {
        super("이미 좋아요를 누른 상태");
    }
}
