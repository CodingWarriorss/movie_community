package com.codeworrisors.Movie_Community_Web.dto.review.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class ReviewCommentResponseDto {
    private Long id;
    private ReviewMemberResponseDto member;
    private String content;
    private Timestamp createDate;
}
