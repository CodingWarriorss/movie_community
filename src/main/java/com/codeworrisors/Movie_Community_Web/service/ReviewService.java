package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Review;

public interface ReviewService {
    Image createImage(Image image);
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(Review review);
}
