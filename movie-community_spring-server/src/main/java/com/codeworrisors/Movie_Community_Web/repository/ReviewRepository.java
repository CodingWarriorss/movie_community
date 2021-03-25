package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    
    public Page<Review> findAllByMember(Pageable pageable, Member member);

    public Page<Review> findAllByMovieTitle(Pageable pageable, String movieTitle);
}
