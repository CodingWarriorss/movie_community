package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.exception.NoAuthCommentStateException;
import com.codeworrisors.Movie_Community_Web.exception.NoCommentElementException;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.CommentRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

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

    public JSONObject updateComment(Member member, UpdateCommentDto updateCommentDto) {
        JSONObject result = new JSONObject();

        commentRepository.findById(updateCommentDto.getCommentId())
                .ifPresentOrElse(comments -> {
                    if (member.getId() != comments.getMember().getId()) {
                        throw new NoAuthCommentStateException();
                    }
                }, NoCommentElementException::new);
        return result;
    }

    public void deleteComment(Member member, long commentId)
            throws  IllegalStateException, NoSuchElementException {
        commentRepository
                .findById(commentId)
                .ifPresentOrElse(comment -> {
                    if (member.getId() != comment.getMember().getId()) {
                        throw new NoAuthCommentStateException();
                    }
                    commentRepository.delete(comment);
                }, NoCommentElementException::new);


    }
}
