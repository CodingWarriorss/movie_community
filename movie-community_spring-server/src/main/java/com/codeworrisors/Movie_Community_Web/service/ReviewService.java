package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.*;
import com.codeworrisors.Movie_Community_Web.dto.review.request.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.review.request.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.review.response.ReviewCommentResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.review.response.ReviewImageResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.review.response.ReviewLikeResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.review.response.ReviewResponseDto;
import com.codeworrisors.Movie_Community_Web.exception.NoMemberElementException;
import com.codeworrisors.Movie_Community_Web.exception.NoReviewElementException;
import com.codeworrisors.Movie_Community_Web.property.StaticResourceProperties;
import com.codeworrisors.Movie_Community_Web.model.*;
import com.codeworrisors.Movie_Community_Web.repository.*;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {
    public static final String SUCCESS_CODE = "SUCCESS";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;


    /*
     * 리뷰
     * */
    public List<Review> getReviews(Pageable pageable, String movieTitle, String memberName, Member member) throws IllegalStateException {
        if (movieTitle != null) return getReviewsByMovieTitle(pageable, movieTitle);
        else if (memberName != null) return getReviewsByMemberName(pageable, memberName, member);
        return getAll(pageable);
    }

    private List<Review> getReviewsByMovieTitle(Pageable pageable, String movieTitle) {
        List<Review> reviews = reviewRepository.findByMovieTitle(pageable, movieTitle).getContent();
        reviews.forEach(review -> {
            review.setLikeCount(review.getCommentsList().size());
            review.setCommentCount(review.getCommentsList().size());
        });
        return reviews;
    }

    private List<Review> getReviewsByMemberName(Pageable pageable, String memberName, Member member) {
        Member member1 = memberRepository
                .findByMemberName(memberName)
                .orElseThrow(NoMemberElementException::new);

       List<Review> reviews = reviewRepository
               .findByMemberId(pageable, memberRepository.findByMemberName(memberName).get().getId()).get().getContent();
        reviews.forEach(review -> {
            review.setLikeCount(review.getLikesList().size());
            review.setCommentCount(review.getCommentsList().size());
        });

        return reviews;
    }

    private List<Review> getAll(Pageable pageable) {
        List<Review> reviews = reviewRepository.findAll(pageable).getContent();
        reviews.forEach(review -> {
            review.setLikeCount(review.getLikesList().size());
            review.setCommentCount(review.getCommentsList().size());
        });

        return reviews;
    }



    public ResponseDto createReview(Member member, CreateReviewDto createReviewDto) throws IOException {
        Review review = reviewRepository.save(
                new Review(createReviewDto.getMovieTitle().replaceAll("\n", ""),
                        createReviewDto.getContent(),
                        createReviewDto.getRating(),
                        member));

        if (createReviewDto.getFiles() != null) {
            saveImages(review, createReviewDto.getFiles());
        }
        return ResponseDto.builder()
                .result(SUCCESS_CODE)
                .build();
    }

    public ResponseDto updateReview(Member member, UpdateReviewDto updateReviewDto) throws IOException {
        Review updateReview = reviewRepository.findById(updateReviewDto.getReviewId())
                .map(review -> review.updateReview(member.getId(), updateReviewDto.getContent(), updateReviewDto.getRating()))
                .orElseThrow(NoReviewElementException::new);

        if (updateReviewDto.getNewFiles() != null) {
            saveImages(updateReview, updateReviewDto.getNewFiles());
        }

        if (updateReviewDto.getDeletedFiles() != null) {
            deleteImages(updateReview.getId(), updateReviewDto.getDeletedFiles());
        }

        return ResponseDto
                .builder()
                .result(SUCCESS_CODE)
                .build();
    }


    public ResponseDto deleteReview(Member member, long reviewId){
        Review deleteReview = reviewRepository.findById(reviewId)
                .map(review -> review.validateReviewAuth(member.getId()))
                .orElseThrow(NoReviewElementException::new);

        reviewRepository.delete(deleteReview);

        return ResponseDto
                .builder()
                .result(SUCCESS_CODE)
                .build();
    }


    private void saveImages(Review review, List<MultipartFile> newFiles) throws IOException {
        for (MultipartFile file : newFiles) {
            String uuidFilename = saveFile(file);
            Image saved = imageRepository.save(new Image(uuidFilename, review));
            review.getImageList().add(saved);
        }


    }

    private void deleteImages(long reviewId, List<Long> deletedFiles) {
        Collections.sort(deletedFiles);
        int idx = 0, cnt = deletedFiles.size();

        // 현재 리뷰 id로 검색한 이미지 리스트에서만 삭제
        for (Image image : imageRepository.findImagesByReviewId(reviewId)) {
            if (cnt == 0) break;
            if (image.getId() != deletedFiles.get(idx)) continue;

            removeFile(image.getFileName());
            imageRepository.delete(image);
            idx++;
            cnt--;
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String uuidFilename = uuid + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
        Files.write(filePath, file.getBytes());
        return uuidFilename;
    }

    private void removeFile(String uuidFilename) {
        try {
            Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
            Files.delete(filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
