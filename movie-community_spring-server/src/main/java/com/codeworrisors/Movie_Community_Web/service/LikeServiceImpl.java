package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LikeServiceImpl implements LikesService {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public boolean checkLike(Member member, Review review) {
//        try {
//            validateDuplicateLike(member, review);
//        } catch (IllegalStateException e) {
//            return false;
//        }
//        return true;
        return true;
    }

    @Override
    public int addLike(Likes like) {
//        if (checkLike(like.getMemeber(), like.getReview())) {
//            return FAILED;
//        }
        likeRepository.save(like);
        return SUCCESS;
    }

    @Override
    public int deleteLike(Likes like) {
//        if (!checkLike(like.getMemeber(), like.getReview())) {
//            return FAILED;
//        }
        likeRepository.delete(like);
        return SUCCESS;
    }

//    private void validateDuplicateLike(Member member, Review review) {
//        likeRepository.findByMemberIdAndReviewId(member, review)
//                .ifPresent(m -> {
//                    throw new IllegalStateException("좋아요를 한 상태입니다.");
//                });
//    }
}
