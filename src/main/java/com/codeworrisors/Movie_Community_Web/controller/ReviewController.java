package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
//@CrossOrigin("*")
public class ReviewController {

    @Value("${file.path}")
    private String fileRealPath;

    private final ReviewService reviewService;

    // (임시)
    Member member = new Member();

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;

        member.setMemberName("anyeji1220"); // 임시
    }

    @PostMapping
    public void uploadReview
    (@RequestParam("movieTitle") String movieTitle,
     @RequestParam("content") String content,
     @RequestParam("file") MultipartFile file) throws IOException {

        // 이미지 업로드
        UUID uuid = UUID.randomUUID(); // 식별자 생성
        String uuidFilename = uuid + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileRealPath + uuidFilename);
        Files.write(filePath, file.getBytes());

        // Image(fk: review) < Review(fk: member) < Member
        Review review = new Review();
        Image image = new Image();
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);

        review.setMovieTitle(movieTitle);
        review.setContent(content);
        review.setImageList(images); // review에서도 Image를 참조
        review.setMember(member);// Review(주인)에 member를 세팅
        reviewService.createReview(review);

        image.setFileName(uuidFilename);
        image.setReview(review); // Image(주인)에 review를 세팅
        reviewService.createImage(image);
    }

//    @PutMapping
    public void updateReview(@RequestBody Review review) {
        // (임시) 실제로는 토큰으로 처리
        review.setMember(member);
        reviewService.updateReview(review);
    }

//    @DeleteMapping
    public void deleteReview(@RequestBody Review review) {
        // (임시) 실제로는 토큰으로 처리
        review.setMember(member);
        reviewService.deleteReview(review);
    }
}
