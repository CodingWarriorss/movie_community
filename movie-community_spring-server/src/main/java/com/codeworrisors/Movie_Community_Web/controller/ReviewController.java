package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.dto.ReviewDTO;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/review")
//@CrossOrigin("*")
public class ReviewController {
    private final static int PAGE_SIZE = 5;

    @Value("${file.path}")
    private String fileRealPath; // 이미지 저장 경로

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(produces = "text/plain; charset=UTF-8")
    public @ResponseBody
    String searchMovie(@RequestParam("title") String title) {
        String result = null;
        try {
            String text = URLEncoder.encode(title, "UTF-8");
            result = reviewService.searchMovie(text);
            System.out.println("[영화 검색 결과] : " + result);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        }

        return result;
    }

    // 이미지 없는 리뷰 업로드
    @PostMapping
    public void uploadReview
    (@AuthenticationPrincipal PrincipalDetails userDetail,
     @RequestParam("movieTitle") String movieTitle,
     @RequestParam("content") String content,
     @RequestParam("rating") int rating) throws IOException {
        // id 세팅
        Member member = userDetail.getMember();
        System.out.println(member);

        // DB 저장 [ Review(fk: member) < Member ]
        Review review = new Review();

        review.setMovieTitle(movieTitle);
        review.setContent(content);
        review.setRating(rating);
        review.setMember(member);// Review(주인)에 member를 세팅
        reviewService.createReview(review);
    }

    // 이미지 있는 리뷰 업로드
    @PostMapping("/img")
    public void uploadReviewWithImg
    (@AuthenticationPrincipal PrincipalDetails userDetail,
     @RequestParam("movieTitle") String movieTitle,
     @RequestParam("content") String content,
     @RequestParam("rating") int rating,
     @RequestParam("file") List<MultipartFile> files) {

        System.out.println("진입");
        files.forEach(file -> {
            System.out.print(file + ",");
        });

        // id 세팅
        Member member = userDetail.getMember();
        System.out.println(member);

        // 이미지 업로드
        ArrayList<String> uuidFilenames = new ArrayList<>();
        files.forEach(file -> {
            UUID uuid = UUID.randomUUID(); // 식별자 생성
            String uuidFilename = uuid + "_" + file.getOriginalFilename();
            uuidFilenames.add(uuidFilename);
            Path filePath = Paths.get(fileRealPath + uuidFilename);
            try {
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 리뷰 테이블 세팅
        Review review = new Review();
        review.setMovieTitle(movieTitle);
        review.setContent(content);
        review.setRating(rating);
        review.setMember(member);// Review(주인)에 member를 세팅
        ArrayList<Image> images = new ArrayList<>();
        review.setImages(images); // review에서도 Image를 참조
        reviewService.createReview(review);

        // 이미지 테이블 세팅
        for (String uuidFilename : uuidFilenames) {
            Image image = new Image();
            image.setFileName(uuidFilename);
            image.setReview(review);
            reviewService.createImage(image); // image 테이블에 row 추가

            images.add(image); // review 테이블에 세팅된 images 자동 update
        }
    }

    // 미완 ====================================================
    //    @PutMapping
    public void updateReview(@RequestBody Review review) {
        reviewService.updateReview(review);
    }

    //    @DeleteMapping
    public void deleteReview(@RequestBody Review review) {
        reviewService.deleteReview(review);
    }

    @GetMapping
    public Page<ReviewDTO> getAllReview(@RequestParam int pageIndex/*, @AuthenticationPrincipal PrincipalDetails userDetail*/) {
        Member currentMember = new Member();
        currentMember.setId(1L);

        Page<ReviewDTO> reviews = reviewService.getReviewData(pageIndex, PAGE_SIZE, currentMember);

        return reviews;
    }
}
