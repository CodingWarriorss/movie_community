package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.property.StaticResourceProperties;
import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.model.*;
import com.codeworrisors.Movie_Community_Web.repository.*;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
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
            review.setLikeCount(likeRepository.countByReviewId(review.getId()));
            review.setCommentCount(commentRepository.countByReviewId(review.getId()));
        });
        return reviews;
    }

    private List<Review> getReviewsByMemberName(Pageable pageable, String memberName, Member member) {
        memberRepository.findByMemberName(memberName).orElseThrow(() -> new NoSuchElementException("Non-existent user"));


        if (! member.getMemberName().equals(memberName))
            throw new AuthorizationServiceException("Access to unauthorized resources");

        List<Review> reviews = reviewRepository.findByMemberId(pageable, member.getId()).getContent();
        reviews.forEach(review -> {
            review.setLikeCount(likeRepository.countByReviewId(review.getId()));
            review.setCommentCount(commentRepository.countByReviewId(review.getId()));
        });
        return reviews;
    }

    private List<Review> getAll(Pageable pageable) {
        List<Review> reviews = reviewRepository.findAll(pageable).getContent();
        reviews.forEach(review -> {
            review.setLikeCount(likeRepository.countByReviewId(review.getId()));
            review.setCommentCount(commentRepository.countByReviewId(review.getId()));
        });

        return reviews;
    }



    public Review createReview(Member member, CreateReviewDto createReviewDto) throws IOException {
        Review review = reviewRepository.save(
                new Review(createReviewDto.getMovieTitle().replaceAll("\n", ""),
                        createReviewDto.getContent(),
                        createReviewDto.getRating(),
                        member));

        if (createReviewDto.getFiles() != null) {
            saveImages(review, createReviewDto.getFiles());
        }
        return review;
    }


    public Review updateReview(Member member, UpdateReviewDto updateReviewDto) throws IllegalStateException, NoSuchElementException, IOException {
        reviewRepository.findById(updateReviewDto.getReviewId())
                .ifPresentOrElse(review -> {
                    if (review.getMember().getId() != member.getId())
                        throw new AuthorizationServiceException("권한 없는 리뷰에 대한 수정 요청");
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 리뷰에 대한 수정 요청");
                });

        Review review = reviewRepository.findById(updateReviewDto.getReviewId()).get();
        review.setContent(updateReviewDto.getContent());
        review.setRating(updateReviewDto.getRating());

        if (updateReviewDto.getNewFiles() != null) {
            saveImages(review, updateReviewDto.getNewFiles());
        }
        if (updateReviewDto.getDeletedFiles() != null) {
            deleteImages(review.getId(), updateReviewDto.getDeletedFiles());
        }
        return review;
    }

    public void deleteReview(Member member, long reviewId) throws IllegalStateException, NoSuchElementException {
        reviewRepository.findById(reviewId).ifPresentOrElse(
                review -> {
                    if (review.getMember().getId() != member.getId())
                        throw new IllegalStateException("권한 없는 리뷰에 대한 삭제 요청");

                    review.getImageList().forEach(image -> removeFile(image.getFileName()));
                    reviewRepository.delete(review);
                },
                () -> {
                    throw new NoSuchElementException("존재하지 않는 리뷰에 대한 삭제 요청");
                });
    }


    private void saveImages(Review review, List<MultipartFile> newFiles) throws IOException {
        JSONArray imageIds = new JSONArray();

        for (MultipartFile file : newFiles) {
            String uuidFilename = saveFile(file);
            Image saved = imageRepository.save(new Image(uuidFilename, review));
            imageIds.add(saved.getId());
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


    /*
     * 댓글
     * */

    public JSONObject updateComment(Member member, UpdateCommentDto updateCommentDto) throws IllegalStateException, NoSuchElementException {
        JSONObject result = new JSONObject();

        commentRepository.findById(updateCommentDto.getCommentId())
                .ifPresentOrElse(comment -> {
                    if (member.getId() != comment.getMember().getId())
                        throw new IllegalStateException("권한 없는 댓글에 대한 수정 요청");
                    comment.setContent(updateCommentDto.getContent());
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 댓글에 대한 수정 요청");
                });

        return result;
    }

    public void deleteComment(Member member, long commentId) throws IllegalStateException, NoSuchElementException {
        commentRepository.findById(commentId)
                .ifPresentOrElse(comment -> {
                    if (member.getId() != comment.getMember().getId())
                        throw new IllegalStateException("권한 없는 댓글에 대한 삭제 요청");
                    commentRepository.delete(comment);
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 댓글에 대한 삭제 요청");
                });
    }
}
