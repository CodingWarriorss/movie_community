package com.codeworrisors.Movie_Community_Web.service;

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

    public Long createLike(Member member, Long reviewId)
            throws NoSuchElementException, IllegalStateException{
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                   throw new NoSuchElementException("존재하지 않는 리뷰");
                });

        likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .ifPresent(like -> {
                    throw new IllegalStateException("이미 좋아요를 누른 상태");
                });

        Likes pressedLike = likeRepository.save(new Likes(member, reviewRepository.getOne(reviewId)));
        return pressedLike.getId();
    }

    public Long deleteLike(Member member, Long reviewId)
        throws NoSuchElementException, IllegalStateException {
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                   throw new NoSuchElementException("존재하지 않는 리뷰");
                });

        Likes deleteLike = likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("이미 unlike 상태");
                });

        likeRepository.delete(deleteLike);

        return deleteLike.getId();
    }
}
