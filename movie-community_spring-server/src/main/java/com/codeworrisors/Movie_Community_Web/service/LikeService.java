package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.LikeResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.ResponseDto;
import com.codeworrisors.Movie_Community_Web.exception.AlreadyPressedLikeStateException;
import com.codeworrisors.Movie_Community_Web.exception.AlreadyUnLikeStateException;
import com.codeworrisors.Movie_Community_Web.exception.NoReviewElementException;
import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.LikeRepository;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public LikeService(LikeRepository likeRepository, ReviewRepository reviewRepository) {
        this.likeRepository = likeRepository;
        this.reviewRepository = reviewRepository;
    }

    public LikeResponseDto createLike(Member member, Long reviewId) {
        reviewRepository.findById(reviewId)
                .orElseThrow(NoReviewElementException::new);

        likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .ifPresent(AlreadyPressedLikeStateException::new);

        Likes likes = likeRepository.save(new Likes(member, reviewRepository.getOne(reviewId)));

        return LikeResponseDto
                .builder()
                .result("success")
                .status("LIKE")
                .likeId(likes.getId())
                .build();
    }

    public LikeResponseDto deleteLike(Member member, Long reviewId)
        throws NoSuchElementException, IllegalStateException {
        reviewRepository.findById(reviewId)
                .orElseThrow(NoReviewElementException::new);

        Likes deleteLike = likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .orElseThrow(AlreadyUnLikeStateException::new);

        likeRepository.delete(deleteLike);

        return LikeResponseDto
                .builder()
                .result("success")
                .status("UNLIKE")
                .likeId(deleteLike.getId())
                .build();
    }
}
