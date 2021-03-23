package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.LikesService;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/like")
public class LikesController {
    private final LikesService likeService;
    private final ReviewService reviewService;

    public LikesController(LikesService likeService, ReviewService reviewService) {
        this.likeService = likeService;
        this.reviewService = reviewService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/add")
    public void addLike(
            @AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestBody Review review
        ) {

        System.out.println("Add Like Method");
        Member currentMember = userDetail.getMember();

        Review reviewEntity = reviewService.getReviewById(review.getId());
        Likes likes = new Likes(currentMember, reviewEntity);
        reviewEntity.getLikes().add(likes);

        likeService.addLike(likes);
    }

//    @PostMapping("/cancel")
//    public void deleteLike(
//            @AuthenticationPrincipal PrincipalDetails userDetail,
//            @RequestBody Review review
//        ) {
//
//        System.out.println("Cancel Like");
//        Member currentMember = userDetail.getMember();
//
//        Review reviewEntity = reviewService.getReviewById(review.getId());
//    }
}
