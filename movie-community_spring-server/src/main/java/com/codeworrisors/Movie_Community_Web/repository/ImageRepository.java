package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByReviewId(long reviewId);
}
