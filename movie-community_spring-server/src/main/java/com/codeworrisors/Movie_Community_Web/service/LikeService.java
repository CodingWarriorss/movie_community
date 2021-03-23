package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;

public interface LikeService {
    int addLike(Likes like);
    int deleteLike(Likes likes);

}
