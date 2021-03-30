package com.codeworrisors.Movie_Community_Web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class CreateReviewDto {
    private String movieTitle;
    private String content;
    private int rating;
    private List<MultipartFile> files;
}
