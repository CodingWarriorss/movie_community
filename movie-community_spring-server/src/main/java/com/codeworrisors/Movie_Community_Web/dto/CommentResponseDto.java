package com.codeworrisors.Movie_Community_Web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String result;
}
