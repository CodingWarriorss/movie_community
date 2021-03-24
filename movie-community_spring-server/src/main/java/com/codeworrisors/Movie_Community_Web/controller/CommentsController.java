package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.CommentsService;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentsController {
    public static final int EXIST = 1;
    public static final int NOT_EXIST = 0;
    public static final int SUCCESS = 1;

    private final CommentsService commentsService;
    private final ReviewService reviewService;

    public CommentsController(CommentsService commentsService, ReviewService reviewService) {
        this.commentsService = commentsService;
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public int addComment(
//            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestBody Review review,
            @ModelAttribute(value = "comment") String comment) {

        Review reviewEntity = reviewService.getReviewById(review.getId());
//        Member currentMember = userDetail.getMember();
        Member currentMember = new Member();
        currentMember.setId(1L);
        Comments comments = new Comments();
        comments.setMember(currentMember);
        comments.setReview(reviewEntity);
        comments.setContent(comment);
//
        reviewEntity.getComments().add(comments);
        commentsService.postComment(comments);
        return SUCCESS;
    }

    @PostMapping("/delete")
    public int deleteComment(@RequestParam long commentsId) {
        Comments comments = commentsService.findCommentsById(commentsId);
        if (comments == null) {
            return NOT_EXIST;
        }
        commentsService.deleteComment(comments);
        return EXIST;
    }

    @PostMapping("/get")
    public List<Comments> getCommentByReviewId(@RequestBody Review review) {
        List<Comments> comments = commentsService.findCommentsByReviewId(review);
        return comments;
    }
}
