package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;

public interface LikesService {
    int addLike(Likes like);
    int deleteLike(Likes likes);
    boolean checkLike(Member member, Review review);
}
