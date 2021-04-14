package com.codeworrisors.Movie_Community_Web.exception;

public class NoAuthReviewStateException extends IllegalStateException {
    public NoAuthReviewStateException() {
        super("리뷰에 관한 권한이 없음");
    }
}
