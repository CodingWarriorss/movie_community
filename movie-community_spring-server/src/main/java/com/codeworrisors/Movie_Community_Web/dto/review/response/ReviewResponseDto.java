package com.codeworrisors.Movie_Community_Web.dto.review.response;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Review;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ReviewResponseDto {
    private Long id;
    private String movieTitle;
    private String content;
    private Integer rating;
    private Timestamp createDate;
    private int likeCount;
    private int commentCount;
    private ReviewMemberResponseDto member;
    private List<ReviewLikeResponseDto> likesList;
    private List<ReviewImageResponseDto> imageList;
    private List<ReviewCommentResponseDto> commentsList;

    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto
                .builder()
                .id(review.getId())
                .movieTitle(review.getMovieTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .createDate(review.getCreateDate())
                .likeCount(review.getLikesList().size())
                .commentCount(review.getCommentsList().size())
                .member(ReviewMemberResponseDto.fromMember(review.getMember()))
                .likesList(convertLikes(review.getLikesList()))
                .imageList(convertImages(review.getImageList()))
                .commentsList(convertComments(review.getCommentsList()))
                .build();
    }

    private static List<ReviewImageResponseDto> convertImages(List<Image> images) {
        List<ReviewImageResponseDto> imagesResponse = new ArrayList<>();
        images.forEach(image -> imagesResponse.add(ReviewImageResponseDto.fromImage(image)));
        return imagesResponse;
    }

    private static List<ReviewCommentResponseDto> convertComments(List<Comments> comments) {
        List<ReviewCommentResponseDto> commentsResponse = new ArrayList<>();

        comments.forEach(comment -> commentsResponse.add(ReviewCommentResponseDto.fromComment(comment)));

        return commentsResponse;
    }

    private static List<ReviewLikeResponseDto> convertLikes(List<Likes> likes) {
        List<ReviewLikeResponseDto> likesResponse = new ArrayList<>();

        likes.forEach(like -> likesResponse.add(ReviewLikeResponseDto.fromLike(like)));

        return likesResponse;
    }
}
