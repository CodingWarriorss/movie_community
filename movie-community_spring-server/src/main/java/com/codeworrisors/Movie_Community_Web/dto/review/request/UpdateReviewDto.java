package com.codeworrisors.Movie_Community_Web.dto.review.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class UpdateReviewDto {
    private long reviewId;
    private String content;
    private int rating;
    private List<MultipartFile> newFiles;
    private List<Long> deletedFiles;
}
