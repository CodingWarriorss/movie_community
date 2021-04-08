package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.LikeResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.ResponseDto;
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
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeResponseDto likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                      @RequestBody Map<String, Long> params) {
        LikeResponseDto response = likeService.createLike(userDetail.getMember(), params.get("reviewId"));

        return response;
    }

    @DeleteMapping
    public LikeResponseDto unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        LikeResponseDto response = likeService.deleteLike(userDetail.getMember(), reviewId);

        return response;
    }
}
