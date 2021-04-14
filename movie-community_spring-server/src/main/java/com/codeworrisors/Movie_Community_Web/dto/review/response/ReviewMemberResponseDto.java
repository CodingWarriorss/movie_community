package com.codeworrisors.Movie_Community_Web.dto.review.response;

import lombok.Builder;

@Builder
public class ReviewMemberResponseDto {
    private Long id;
    private String memberName;
    private String profileImg;
}
