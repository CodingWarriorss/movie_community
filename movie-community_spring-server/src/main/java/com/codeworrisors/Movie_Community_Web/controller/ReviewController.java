package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {
    static final String SUCCESS = "SUCCESS";
    static final String FAIL = "FAIL";

    static final String RESULT = "result";
    static final String REVIEW = "review";
    static final String COMMENT = "comment";
    static final int PAGE_SIZE = 5;

    private final ReviewService reviewService;
//    private final CommentService commentService;
//    private final LikeService likeService;

    public ReviewController(ReviewService reviewService
//            ,
//                            CommentService commentService,
//                            LikeService likeService
    ) {
        this.reviewService = reviewService;
//        this.commentService = commentService;
//        this.likeService = likeService;
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


    /*
     * 댓글 생성
     * response(성공/실패 여부, 만들어진 commentId)
     * */
    @PostMapping("/comment")
    public JSONObject postComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                  @RequestBody CreateCommentDto createCommentDto) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            response.put(COMMENT, reviewService.createComment(userDetail.getMember(), createCommentDto));
        } catch (EntityNotFoundException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 댓글 추가 요청");
        }

        return response;
    }

    /*
     * 댓글 수정 => POSTMAN에서만 해봄
     * response(성공/실패 여부)
     * */
    @PutMapping("/comment")
    public JSONObject modifyComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    UpdateCommentDto updateCommentDto) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            reviewService.updateComment(userDetail.getMember(), updateCommentDto);
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 댓글에 대한 수정 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 댓글에 대한 수정 요청");
        }

        return response;
    }

    /*
     * 댓글 삭제 => POSTMAN에서만 해봄
     * response(성공/실패 여부)
     * */
    @DeleteMapping("/comment")
    public JSONObject deleteComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestParam long commentId) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            reviewService.deleteComment(userDetail.getMember(), commentId);
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 댓글에 대한 삭제 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 댓글에 대한 삭제 요청");
        }

        return response;
    }


    /*
     * 좋아요 추가/삭제
     * response(LIKE/UNLIKE 여부)
     * */
    @PostMapping("/like")
    public JSONObject likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                 @RequestBody Map<String, Long> params) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            reviewService.createLike(userDetail.getMember(), params.get("reviewId"));
            response.put("status", "LIKE");
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/이미 LIKE 상태");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 LIKE 요청");
        }

        return response;
    }

    @DeleteMapping("/like")
    public JSONObject unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            reviewService.deleteLike(userDetail.getMember(), reviewId);
            response.put("status", "UNLIKE");
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/이미 UNLIKE 상태");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 UNLIKE 요청");
        }

        return response;
    }
}
