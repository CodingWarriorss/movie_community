package com.codeworrisors.Movie_Community_Web.dto;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ReviewDTO {
    private long reviewId;
    private String writerName;
    private Timestamp createDate;
    private String thumbnailUrl;
    private String movieTitle;
    private String content;
    private List<Image> images;
    private int rating;
    private int likes;
    private boolean likePressed = false;
    private List<Comments> comments;
}
