package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.*;
import com.codeworrisors.Movie_Community_Web.dto.review.request.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.review.request.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.review.response.ReviewImageResponseDto;
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


    public List<ReviewResponseDto> getReviews(Pageable pageable, String movieTitle, String memberName) throws IllegalStateException {
        if (movieTitle != null) return getReviewsByMovieTitle(pageable, movieTitle);
        else if (memberName != null) return getReviewsByMemberName(pageable, memberName);
        return getAll(pageable);
    }

    private List<ReviewResponseDto> getReviewsByMovieTitle(Pageable pageable, String movieTitle) {
        List<ReviewResponseDto> reviews = reviewRepository.findByMovieTitle(pageable, movieTitle)
                .map(review -> ReviewResponseDto.fromEntity(review))
                .getContent();

        return reviews;
    }

    private List<ReviewResponseDto> getReviewsByMemberName(Pageable pageable, String memberName) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(NoMemberElementException::new);
        List<ReviewResponseDto> reviews = reviewRepository.findByMemberId(pageable, member.getId())
                .map(review -> ReviewResponseDto.fromEntity(review))
                .getContent();
        return reviews;
    }

    private List<ReviewResponseDto> getAll(Pageable pageable) {
        List<ReviewResponseDto> reviews =  reviewRepository.findAll(pageable)
                .map(ReviewResponseDto::fromEntity)
                .getContent();
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
            List<ReviewImageResponseDto> updateImages = saveImages(updateReview, updateReviewDto.getNewFiles());
            List<Image> previousImages = imageRepository.findImagesByReviewId(updateReviewDto.getReviewId());

           if (previousImages != null) {
               for (Image image : previousImages) {
                   updateImages.add(ReviewImageResponseDto.builder().id(image.getId()).fileName(image.getFileName()).build());
               }
           }
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


    private List<ReviewImageResponseDto> saveImages(Review review, List<MultipartFile> newFiles) throws IOException {
        JSONArray imageIds = new JSONArray();
        List<ReviewImageResponseDto> updateImages = new ArrayList<>();

        for (MultipartFile file : newFiles) {
            String uuidFilename = saveFile(file);
            Image saved = imageRepository.save(new Image(uuidFilename, review));
            imageIds.add(saved.getId());

            updateImages.add(ReviewImageResponseDto.builder().id(saved.getId()).fileName(saved.getFileName()).build());
        }

        return updateImages;
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
