package com.codeworrisors.Movie_Community_Web.dto.review.response;

import lombok.Builder;

@Builder
public class ReviewLikeResponseDto {
    private Long id;
    private ReviewMemberResponseDto member;
}
