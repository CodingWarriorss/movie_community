package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.CommentRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private ReviewRepository reviewRepository;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }

    public JSONObject createComment(Member member, CreateCommentDto createCommentDto)
            throws EntityNotFoundException{
        JSONObject result = new JSONObject();

        reviewRepository.findById(createCommentDto.getReviewId())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        Comments savedComments =
                new Comments(
                        createCommentDto.getContent(),
                        member,
                        reviewRepository.getOne(createCommentDto.getReviewId())
                );

        result.put("commentId", savedComments.getId());
        return result;
    }

    public \
}
