package com.codeworrisors.Movie_Community_Web.dto.comment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentDto {
    private long commentId;
    private String content;
}
