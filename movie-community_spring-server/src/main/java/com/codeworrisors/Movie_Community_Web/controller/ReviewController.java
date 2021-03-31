package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.dto.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {
    static final String RESULT = "result";
    static final String REVIEW = "review";
    static final String SUCCESS = "SUCCESS";
    static final String FAIL = "FAIL";

    private final static int PAGE_SIZE = 5;

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /*
     * 전체 정보 조회 => 아직 불안불안하다
     * */
    @GetMapping
    public List<Review> seeReview(@RequestParam int pageIndex,
                                  @RequestParam(required = false) String movieTitle,
                                  @RequestParam(required = false) Long memberId) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate"));

        if (movieTitle != null) { // 있으면 JSONObject, 없으면 빈 리스트
            return reviewService.getReviewsByMovieTitle(pageable, movieTitle.trim());
        } else if (memberId != null) { // 있으면 JSONObject, 없으면 null
            try {
                return reviewService.getReviewsByMemberId(pageable, memberId);
            } catch (IllegalStateException e) {
                System.out.println("존재하지 않는 회원");
                return null;
            }
        }
        return reviewService.getAll(pageable);
    }


    /*
     * 리뷰 업로드
     * response(성공/실패 여부, 만들어진 reviewId, 만들어진 imageIds)
     * */
    @PostMapping
    public JSONObject uploadReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   CreateReviewDto createReviewDto) {

        JSONObject response = new JSONObject();
        try {
            response.put(RESULT, SUCCESS);
            response.put(REVIEW, reviewService.createReview(userDetail.getMember(), createReviewDto));
        } catch (IOException e) {
            response.put(RESULT, FAIL + "/이미지 저장 오류");
        }

        return response;
    }

    /*
     * 리뷰 수정
     * response(성공/실패 여부, 만들어진 imageIds)
     * */
    @PutMapping
    public JSONObject modifyReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   UpdateReviewDto updateReviewDto) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            response.put(REVIEW, reviewService.updateReview(userDetail.getMember(), updateReviewDto));
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 리뷰에 대한 수정 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 수정 요청");
        } catch (IOException e) {
            response.put(RESULT, FAIL + "/이미지 저장 오류");
        }

        return response;
    }


    /*
     * 리뷰 삭제
     * response(성공/실패 여부)
     * */
    @DeleteMapping
    public JSONObject deleteReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            reviewService.deleteReview(userDetail.getMember(), reviewId);
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 리뷰에 대한 삭제 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 삭제 요청");
        }

        return response;
    }
}
