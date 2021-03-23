package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.CommentsService;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentsController {

    private final CommentsService commentsService;
    private final ReviewService reviewService;

    public CommentsController(CommentsService commentsService, ReviewService reviewService) {
        this.commentsService = commentsService;
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public int addComment(
            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestBody Review review,
            @RequestParam("content") String content) {
        Review reviewEntity = reviewService.getReviewById(review.getId());
        Member currentMember = userDetail.getMember();

        Comments comments = new Comments();
        comments.setMember(currentMember);
        comments.setReview(reviewEntity);
        comments.setContent(content);

        commentsService.postComment(comments);
        return 1;
    }
}
