package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.BoxOfficeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ranking")
public class BoxOfficeRankingController {

    private final BoxOfficeRankingService boxOfficeRankingService;

    @GetMapping
    public List getBoxOfficeRanking(@AuthenticationPrincipal PrincipalDetails userDetail) {
        return boxOfficeRankingService.getBoxOfficeRanking();
    }
}
