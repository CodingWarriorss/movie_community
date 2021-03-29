package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.service.BoxOfficeRankingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class BoxOfficeRankingController {

    private BoxOfficeRankingService boxOfficeRankingService;

    public BoxOfficeRankingController(BoxOfficeRankingService boxOfficeRankingService) {
        this.boxOfficeRankingService = boxOfficeRankingService;
    }

    @GetMapping
    public List getBoxOfficeRanking(){
        return boxOfficeRankingService.getBoxOfficeRanking();
    }
}
