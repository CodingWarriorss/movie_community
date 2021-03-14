package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
//@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    // (임시)
    Member member = new Member();

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;

        member.setMemberId("anyeji1220"); // 임시
    }
    
    @PostMapping
    public void createReview(@RequestBody Review review) {
        // (임시) 실제로는 토큰으로 처리
        review.setMember(member);
        reviewService.createReview(review);
    }

    @PutMapping
    public void updateReview(@RequestBody Review review) {
        // (임시) 실제로는 토큰으로 처리
        review.setMember(member);
        reviewService.updateReview(review);
    }

    @DeleteMapping
    public void deleteReview(@RequestBody Review review) {
        // (임시) 실제로는 토큰으로 처리
        review.setMember(member);
        reviewService.deleteReview(review);
    }
}
