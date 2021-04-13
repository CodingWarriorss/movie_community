package com.codeworrisors.Movie_Community_Web.dto.like;

import lombok.Builder;

@Builder
public class LikeResponseDto {
    public String result;
    public String status;
    public Long likeId;
}
