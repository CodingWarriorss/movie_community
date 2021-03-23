package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;

public interface LikeService {
    int addLike(Member member, Review review);
    int deleteLike(Member member, Review review);

}
