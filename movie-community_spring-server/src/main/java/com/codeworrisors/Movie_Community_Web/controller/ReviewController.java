package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.*;
import com.codeworrisors.Movie_Community_Web.dto.review.request.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.review.request.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {
    static final String SUCCESS = "SUCCESS";
    static final String FAIL = "FAIL";
    static final String RESULT = "result";
    static final String REVIEW = "review";
    static final String COMMENT = "comment";
    static final int PAGE_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewService reviewService;

    /*
     * 리뷰 CRUD
     * */
    @GetMapping
    public List<Review> seeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                  @RequestParam int pageIndex,
                                  @RequestParam(required = false) String movieTitle,
                                  @RequestParam(required = false) String memberName) {
        try {
            return reviewService.getReviews(
                    PageRequest.of(pageIndex, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate")),
                    movieTitle,
                    memberName,
                    userDetail.getMember());
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseDto uploadReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @ModelAttribute CreateReviewDto createReviewDto) throws IOException {
        return reviewService.createReview(userDetail.getMember(), createReviewDto);
    }

    @PutMapping
    public ResponseDto modifyReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @ModelAttribute UpdateReviewDto updateReviewDto) throws IOException {
        return reviewService.updateReview(userDetail.getMember(), updateReviewDto);
    }

    @DeleteMapping
    public ResponseDto deleteReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        return reviewService.deleteReview(userDetail.getMember(), reviewId);
    }
}
