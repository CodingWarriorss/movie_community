package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.ReviewDTO;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.request.ReviewRequest;

import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    String searchMovie(String title) throws IOException;
    Image createImage(Image image);
    Review createReview(Review review);
    Review updateReview(Review review);
    void deleteReview(Review review);
    Review getReviewById(long reviewId);
    Page<ReviewDTO> getReviewData(int index, int size , Member currentMember, ReviewDTO reviewFilter);
}
