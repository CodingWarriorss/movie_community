package com.codeworrisors.Movie_Community_Web.dto.review.response;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

@Builder
public class ReviewResponseDto {
    private Long id;
    private String movieTitle;
    private String content;
    private Integer rating;
    private Timestamp createDate;
    private int likeCount;
    private int commentCount;
    private ReviewMemberResponseDto member;
    private List<ReviewLikeResponseDto> likeList;
    private List<ReviewImageResponseDto> imageList;
    private List<ReviewCommentResponseDto> commentList;
}
