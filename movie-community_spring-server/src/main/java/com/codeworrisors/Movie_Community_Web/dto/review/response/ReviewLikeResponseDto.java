package com.codeworrisors.Movie_Community_Web.dto.review.response;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewLikeResponseDto {
    private Long id;
    private ReviewMemberResponseDto member;

    public static ReviewLikeResponseDto fromLike (Likes likes) {
        return ReviewLikeResponseDto
                .builder()
                .id(likes.getId())
                .member(ReviewMemberResponseDto.fromMember(likes.getMember()))
                .build();
    }
}
