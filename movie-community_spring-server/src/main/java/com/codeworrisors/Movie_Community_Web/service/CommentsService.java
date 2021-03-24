package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Review;

import java.util.List;

public interface CommentsService {
    void postComment(Comments comments);
    void deleteComment(Comments comments);
    Comments findCommentsById(Long commentsId);
    List<Comments> findCommentsByReviewId(Review review);
}
