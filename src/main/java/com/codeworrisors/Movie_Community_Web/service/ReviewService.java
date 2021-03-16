package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Review;

public interface ReviewService {
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(Review review);
}
