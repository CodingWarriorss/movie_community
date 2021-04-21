package com.codeworrisors.Movie_Community_Web.dto.review.response;

import com.codeworrisors.Movie_Community_Web.model.Image;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewImageResponseDto {
    private Long id;
    private String fileName;

    public static ReviewImageResponseDto fromImage (Image image) {
        return ReviewImageResponseDto
                .builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .build();
    }
}
