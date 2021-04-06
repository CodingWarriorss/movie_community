package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.LikeService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review/like")
public class LikeController {
    public static final String RESULT = "result";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String ERROR_MESSAGE_DELIMITER = "/";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public JSONObject likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                 @RequestBody Map<String, Long> params) {
        JSONObject response = new JSONObject();

        response.put(RESULT, SUCCESS);

        try {
            Long pressedLike = likeService.createLike(userDetail.getMember(), params.get("reviewId"));
            response.put("status", "LIKE");
            response.put("likeId" , pressedLike);
        } catch (IllegalStateException | NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + ERROR_MESSAGE_DELIMITER + e.getMessage());
        }

        return response;
    }

    @DeleteMapping
    public JSONObject unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        JSONObject response  = new JSONObject();

        response.put(RESULT, SUCCESS);

        try {
            Long deleteLike = likeService.deleteLike(userDetail.getMember(), reviewId);
            response.put("status", "UNLIKE");
            response.put("likeId", deleteLike);
        } catch (IllegalStateException | NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + ERROR_MESSAGE_DELIMITER + e.getMessage());
        }

        return response;
    }
}
