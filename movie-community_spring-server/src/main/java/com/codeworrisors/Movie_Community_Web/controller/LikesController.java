package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.LikeService;
import org.json.simple.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review/like")
public class LikesController {
    static final String RESULT = "result";
    static final String SUCCESS = "SUCCESS";
    static final String FAIL = "FAIL";

    private final LikeService likeService;

    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    /*
     * 좋아요 추가/삭제
     * response(LIKE/UNLIKE 여부)
     * */
    @PostMapping
    public JSONObject likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                 @RequestBody Map<String, Long> params) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            likeService.createLike(userDetail.getMember(), params.get("reviewId"));
            response.put("status", "LIKE");
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/이미 LIKE 상태");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 LIKE 요청");
        }

        return response;
    }

    @DeleteMapping
    public JSONObject unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            likeService.deleteLike(userDetail.getMember(), reviewId);
            response.put("status", "UNLIKE");
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/이미 UNLIKE 상태");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 UNLIKE 요청");
        }

        return response;
    }
}
