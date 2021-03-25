package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.constant.StaticResourceProperties;
import com.codeworrisors.Movie_Community_Web.dto.ReviewDTO;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.request.ReviewRequest;
import com.codeworrisors.Movie_Community_Web.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
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
// @CrossOrigin("*")
public class ReviewController {
    private final static int PAGE_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.path}")
    private String fileRealPath; // 이미지 저장 경로

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Review 등록
    @PostMapping
    public void uploadReview(@AuthenticationPrincipal PrincipalDetails userDetail,ReviewDTO reviewRequest)
            throws IOException {

        logger.info("Review Regist");
        // id 세팅
        Member member = userDetail.getMember();

        // 이미지 업로드
        ArrayList<String> uuidFilenames = new ArrayList<>();

        ArrayList<Image> images = new ArrayList<>();

        if (reviewRequest.getFiles() != null) {
            reviewRequest.getFiles().forEach(file -> {
                UUID uuid = UUID.randomUUID(); // 식별자 생성
                String uuidFilename = uuid + "_" + file.getOriginalFilename();
                uuidFilenames.add(uuidFilename);
                Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
                try {
                    Files.write(filePath, file.getBytes());
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            });

        }

        // DB 저장 [ Review(fk: member) < Member ]
        Review review = new Review();

        review.setMovieTitle(reviewRequest.getMovieTitle());
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());
        review.setMember(member); // Review(주인)에 member를 세팅

        if (reviewRequest.getFiles() != null) {
            review.setImages(images); // review에서도 Image를 참조
        }
        reviewService.createReview(review);

        // image table setting
        if (reviewRequest.getFiles() != null) {
            for (String uuidFilename : uuidFilenames) {
                Image image = new Image();
                image.setFileName(uuidFilename);
                image.setReview(review);
                reviewService.createImage(image); // image 테이블에 row 추가
                images.add(image); // review 테이블에 세팅된 images 자동 update
            }
        }

    }

    // 미완 ====================================================
    // @PutMapping
    public void updateReview(@RequestBody Review review) {
        reviewService.updateReview(review);
    }

    // @DeleteMapping
    public void deleteReview(@RequestBody Review review) {
        reviewService.deleteReview(review);
    }

    @GetMapping
    public Page<ReviewDTO> getAllReview(@AuthenticationPrincipal PrincipalDetails userDetail,
            @RequestParam int pageIndex, @RequestParam(defaultValue = "") String movieTitle,
            @RequestParam(defaultValue = "") String writerId) {
        Member currentMember = userDetail.getMember();

        ReviewDTO reviewFilter = new ReviewDTO();

        if( movieTitle.length() > 0){
            reviewFilter.setMovieTitle(movieTitle);
        }

        if( writerId.length() > 0){
            reviewFilter.setWriterName(writerId);
        }

        
        Page<ReviewDTO> reviews = reviewService.getReviewData(pageIndex, PAGE_SIZE, currentMember, reviewFilter);
        return reviews;
    }
}
