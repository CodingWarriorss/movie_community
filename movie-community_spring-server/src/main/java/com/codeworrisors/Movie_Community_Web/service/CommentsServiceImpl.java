package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.CommentsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.List;


@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {
    private CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public void postComment(Comments comments) {
        commentsRepository.save(comments);
    }

    @Override
    public void deleteComment(Comments comments) {
        commentsRepository.delete(comments);
    }

    @Override
    public Comments findCommentsById(Long commentsId) {
        try {
            return commentsRepository.findById(commentsId).get();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    @Override
    public List<Comments> findCommentsByReviewId(Review review) {
        return commentsRepository.findAllByReview(review);
    }
}
