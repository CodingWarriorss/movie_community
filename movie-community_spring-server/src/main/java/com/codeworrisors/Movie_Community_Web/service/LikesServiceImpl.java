package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Likes;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.LikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LikesServiceImpl implements LikesService {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    public static final int NOT_EXIST = -1;

    private final LikeRepository likeRepository;

    public LikesServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean checkLike(Member member, Review review) {
        try {
            validateDuplicateLike(member, review);
        } catch (IllegalStateException e) {
            System.out.println("이미 좋아요 눌렀습니다.");
            return false;
        }
        return true;
    }

    @Override
    public int addLike(Likes like) {
        System.out.println("add Like Service  Method");
        if (checkLike(like.getMember(), like.getReview())) {
            likeRepository.save(like);
            return SUCCESS;
        }

        return FAILED;
    }


    private void validateDuplicateLike(Member member, Review review) {
        likeRepository.findByMemberAndReview(member, review)
                .ifPresent(l -> {
                    throw new IllegalStateException("좋아요 누른 사람");
                });
    }

    @Override
    public long getLikeIdByMemberAndReview(Likes likes) {
        Optional<Likes> likesEntity = likeRepository.findByMemberAndReview(likes.getMember(), likes.getReview());

        if (likesEntity.isEmpty()) {
            return NOT_EXIST;
        }

        return likesEntity.get().getId();
    }

    @Override
    public void deleteById(long likeId) {
        likeRepository.deleteById(likeId);
    }
}
