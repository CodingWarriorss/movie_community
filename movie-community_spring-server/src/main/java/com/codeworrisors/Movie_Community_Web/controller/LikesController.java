package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.LikesService;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/like")
public class LikesController {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    public static final int NOT_EXIST = -1;

    private final LikesService likeService;
    private final ReviewService reviewService;

    public LikesController(LikesService likeService, ReviewService reviewService) {
        this.likeService = likeService;
        this.reviewService = reviewService;
    }

    /*
    return value : SUCCESS : 1, FAILED : 0
    * */
    @PostMapping
    public int addLike(
            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestBody Map<String, Long> params
            ) {
        Member currentMember = userDetail.getMember();
        long reviewId = params.get("reviewId");
        Review reviewEntity = reviewService.getReviewById(reviewId);

        Likes likes = new Likes(currentMember, reviewEntity);

        if (likeService.getLikeIdByMemberAndReview(likes) == NOT_EXIST) {
            likeService.addLike(likes);
            reviewEntity.getLikes().add(likes);
            return SUCCESS;
        }

        return FAILED;
    }

    @DeleteMapping
    public int deleteLike(
            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestParam Long reviewId
        ) {
        System.out.println("Delete Method");
        Member currentMember = userDetail.getMember();
        System.out.println(reviewId);
        Review review = new Review();
        review.setId(reviewId);

        Likes likes = new Likes(currentMember, review);
        long res = likeService.getLikeIdByMemberAndReview(likes);

        if (res > 0) {
            likeService.deleteById(res);
            return SUCCESS;
        }

        return FAILED;
    }
}
