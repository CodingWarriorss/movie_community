package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.LikeResponseDto;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.LikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/api/review/like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeResponseDto likeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                      @RequestParam("reviewId") long reviewId) {
        return likeService.createLike(userDetail.getMember(), reviewId);
    }

    @DeleteMapping
    public LikeResponseDto unlikeReview(@AuthenticationPrincipal PrincipalDetails userDetail,
                                   @RequestParam("reviewId") long reviewId) {
        return likeService.deleteLike(userDetail.getMember(), reviewId);
    }
}
