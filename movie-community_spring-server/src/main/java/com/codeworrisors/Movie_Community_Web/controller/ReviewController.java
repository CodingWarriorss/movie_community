package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public List<Review> seeReview(@RequestParam int pageIndex,
                                  @RequestParam(required = false) String movieTitle,
                                  @RequestParam(required = false) Long memberId) {
        try {
            return reviewService.getReviews(
                    PageRequest.of(pageIndex, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate")),
                    movieTitle,
                    memberId);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("존재하지 않는 회원");
        }
    }

    @PostMapping
    public JSONObject uploadReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   CreateReviewDto createReviewDto) {
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        try {
            response.put(REVIEW, reviewService.createReview(userDetail.getMember(), createReviewDto));
        } catch (IOException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }

    @PutMapping
    public JSONObject modifyReview(@AuthenticationPrincipal PrincipalDetails userDetail, UpdateReviewDto updateReviewDto) {
        JSONObject response = new JSONObject();

        logger.info("Receive update called");
        response.put(RESULT, SUCCESS);

        try {
            response.put(REVIEW, reviewService.updateReview(userDetail.getMember(), updateReviewDto));
        } catch (IllegalStateException | NoSuchElementException | IOException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    };

    @DeleteMapping
    public JSONObject deleteReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response = new JSONObject();

        logger.info( " Delete review Called - delete reivewId : {}"  , reviewId);
        response.put(RESULT, SUCCESS);

        try {
            reviewService.deleteReview(userDetail.getMember(), reviewId);
        } catch (IllegalStateException | NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }


    /*
     * 댓글 CRUD
     * */
    @PostMapping("/comment")
    public JSONObject postComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                  @RequestBody CreateCommentDto createCommentDto) {
        JSONObject response = new JSONObject();

        logger.info("Comment write called - Comment content : {}" , createCommentDto.getContent());
        response.put(RESULT, SUCCESS);



        try {
            response.put(COMMENT, reviewService.createComment(userDetail.getMember(), createCommentDto));
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }


    @PutMapping("/comment")
    public JSONObject modifyComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    UpdateCommentDto updateCommentDto) {
        JSONObject response = new JSONObject();

        response.put(RESULT, SUCCESS);

        try {
            reviewService.updateComment(userDetail.getMember(), updateCommentDto);
        } catch (IllegalStateException | NoSuchElementException e) {
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/comment")
    public JSONObject deleteComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestParam long commentId) {
        logger.info( " Comment delete Called - commentId : {}"  , commentId);
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        try {
            reviewService.deleteComment(userDetail.getMember(), commentId);
        } catch (IllegalStateException | NoSuchElementException e) {
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }


    /*
     * 좋아요 CD
     * */
    @PostMapping("/like")
    public JSONObject likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                 @RequestBody Map<String, Long> params) {
        JSONObject response = new JSONObject();

        logger.info( " Like Called - like reivewId : {}"  , params.get("reviewId"));
        response.put(RESULT, SUCCESS);

        try {
            reviewService.createLike(userDetail.getMember(), params.get("reviewId"));
            response.put("status", "LIKE");
        } catch (IllegalStateException | NoSuchElementException e) {
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/like")
    public JSONObject unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        logger.info( " Like Delete Called - delete reivewId : {}"  , reviewId);

        try {
            reviewService.deleteLike(userDetail.getMember(), reviewId);
            response.put("status", "UNLIKE");
        } catch (IllegalStateException | NoSuchElementException e) {
            response.put(RESULT, FAIL + "/" + e.getMessage());
        }

        return response;
    }
}
