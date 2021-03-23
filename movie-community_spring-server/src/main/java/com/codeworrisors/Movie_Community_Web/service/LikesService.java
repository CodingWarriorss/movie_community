package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;

public interface LikesService {
    int addLike(Likes like);

    boolean checkLike(Member member, Review review);

    long getLikeIdByMemberAndReview(Likes likes);

    void deleteById(long likeId);
}
