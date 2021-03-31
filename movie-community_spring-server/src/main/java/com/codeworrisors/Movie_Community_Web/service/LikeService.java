package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.LikeRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class LikeService {

    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    public LikeService(ReviewRepository reviewRepository, LikeRepository likeRepository) {
        this.reviewRepository = reviewRepository;
        this.likeRepository = likeRepository;
    }

    /*
     * CREATE, DELETE LIKE
     * */
    public void createLike(Member member, long reviewId) throws IllegalStateException, NoSuchElementException {
        if (reviewRepository.findById(reviewId).isEmpty())
            throw new NoSuchElementException("존재하지 않는 리뷰");

        likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .ifPresentOrElse(like -> {
                    throw new IllegalStateException("이미 like한 상태");
                }, () -> {
                    likeRepository.save(new Likes(member, reviewRepository.getOne(reviewId)));
                });
    }

    public void deleteLike(Member member, long reviewId) throws IllegalStateException, NoSuchElementException {
        if (reviewRepository.findById(reviewId).isEmpty())
            throw new NoSuchElementException("존재하지 않는 리뷰");

        likeRepository.findByMemberIdAndReviewId(member.getId(), reviewId)
                .ifPresentOrElse(like -> {
                    likeRepository.delete(like);
                }, () -> {
                    throw new IllegalStateException("이미 unlike 상태");
                });
    }
}
