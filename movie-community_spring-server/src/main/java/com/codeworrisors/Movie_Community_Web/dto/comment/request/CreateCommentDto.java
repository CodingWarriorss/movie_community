package com.codeworrisors.Movie_Community_Web.dto.comment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDto {
    private long reviewId;
    private String content;
}
