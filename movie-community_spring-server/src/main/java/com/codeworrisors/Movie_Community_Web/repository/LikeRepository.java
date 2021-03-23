package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long>, JpaSpecificationExecutor<Likes> {
    @Query(value = "SELECT Likes FROM Likes WHERE Likes.memeber = :member AND Likes.review = :review")
    <Optional> Likes findByMemberIdAndReviewId(Member member, Review review);
}
