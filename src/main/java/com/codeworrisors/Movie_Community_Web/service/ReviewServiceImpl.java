package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public int createReview(Review review) {
        reviewRepository.save(review);
        return 1;
    }

    @Override
    public int updateReview(Review review) {
        if (reviewRepository.findById(review.getReviewId()).isEmpty())
            return -1;
        reviewRepository.save(review);
        return 1;
    }

    @Override
    public int deleteReview(Review review) {
        if (reviewRepository.findById(review.getReviewId()).isEmpty())
            return -1;
        reviewRepository.delete(review);
        return 1;
    }
}
