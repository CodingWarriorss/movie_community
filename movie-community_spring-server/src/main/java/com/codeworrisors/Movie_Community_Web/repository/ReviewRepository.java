package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
}
