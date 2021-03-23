package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.repository.CommentsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {
    private CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void postComment(Comments comments) {
        commentsRepository.save(comments);
    }

    public void deleteComment(Comments comments) {
        commentsRepository.delete(comments);
    }
}
