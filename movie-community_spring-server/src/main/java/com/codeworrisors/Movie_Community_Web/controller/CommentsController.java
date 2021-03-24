package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.CommentsService;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping
    public Map<String, String> addComment(
            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestBody Map<String, String> params
            ) {
        Map<String, String> map = new HashMap<>();
        System.out.println(params.get("reviewId"));
        System.out.println(params.get("content"));
        Review reviewEntity = reviewService.getReviewById(Long.parseLong(params.get("reviewId")));
        Member currentMember = userDetail.getMember();

        Comments comments = new Comments();
        comments.setMember(currentMember);
        comments.setReview(reviewEntity);
        comments.setContent(params.get("content"));

        reviewEntity.getComments().add(comments);
        commentsService.postComment(comments);
        map.put("id", currentMember.getMemberName());

        return map;
    }

    @DeleteMapping
    public int deleteComment(@RequestParam long commentsId) {
        Comments comments = commentsService.findCommentsById(commentsId);
        if (comments == null) {
            return NOT_EXIST;
        }
        commentsService.deleteComment(comments);
        return EXIST;
    }
}