package com.codeworrisors.Movie_Community_Web.dto.review.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class ReviewImageResponseDto implements Serializable {
    private Long id;
    private String fileName;
}
