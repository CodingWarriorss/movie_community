package com.codeworrisors.Movie_Community_Web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.codeworrisors.Movie_Community_Web.service.NaverMovieAPIService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    네이버 영화 조회 API사용 Controller
    CORS문제로 서버를 거쳐서 받아옴
*/
@RestController
@RequestMapping("/api/naver/movie")
public class NaverMovieAPIController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NaverMovieAPIService naverMovieAPIService;

    public NaverMovieAPIController(NaverMovieAPIService naverMovieAPIService) {
        this.naverMovieAPIService = naverMovieAPIService;
    }

    @GetMapping(produces = "application/json; charset=UTF-8")
    public String searchMovie(@RequestParam("title") String title) {
        logger.info("네이버 영화 조회");
        String result = null;
        try {
            String text = URLEncoder.encode(title, "UTF-8");
            result = naverMovieAPIService.searchMovie(text);

        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("검색어 인코딩 실패", e);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("API 요청과 응답 실패", e);
        }

        return result;
    }
}
