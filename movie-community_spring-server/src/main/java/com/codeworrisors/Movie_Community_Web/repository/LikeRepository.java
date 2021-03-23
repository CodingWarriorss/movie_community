package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberIdAndReviewId(Long memberId, Long reviewId);
}
