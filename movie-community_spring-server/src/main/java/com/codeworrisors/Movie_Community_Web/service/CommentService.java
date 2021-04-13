package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.comment.response.CommentResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.comment.request.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.comment.request.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.exception.NoCommentElementException;
import com.codeworrisors.Movie_Community_Web.exception.NoReviewElementException;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.CommentRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;


@Service
public class CommentService {
    public static final String SUCCESS_CODE = "SUCCESS";

    private CommentRepository commentRepository;
    private ReviewRepository reviewRepository;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }

    public CommentResponseDto createComment(Member member, CreateCommentDto createCommentDto)
            throws NoSuchElementException {
        reviewRepository.findById(createCommentDto.getReviewId())
                .orElseThrow(NoReviewElementException::new);

        Comments savedComments = commentRepository.save(
                new Comments(createCommentDto.getContent(), member, reviewRepository.getOne(createCommentDto.getReviewId())));

        return CommentResponseDto
                .builder()
                .commentId(savedComments.getId())
                .result(SUCCESS_CODE)
                .build();
    }

    public CommentResponseDto updateComment(Member member, UpdateCommentDto updateCommentDto)
            throws NoSuchElementException{
        Comments updateComments = commentRepository
                .findById(updateCommentDto.getCommentId())
                .filter(comments -> comments.getMember().getId() == member.getId())
                .map(comments -> {
                    comments.setContent(updateCommentDto.getContent());
                    return comments;
                })
                .orElseThrow(NoCommentElementException::new);

        return CommentResponseDto
                .builder()
                .commentId(updateComments.getId())
                .result(SUCCESS_CODE)
                .build();
    }

    public CommentResponseDto updateComment2(Member member, UpdateCommentDto updateCommentDto) {
        Comments updateComments = commentRepository.findById(updateCommentDto.getCommentId())
                .map(comments -> comments.updateContents(member.getId(), updateCommentDto.getContent()))
                .orElseThrow(NoCommentElementException::new);

        return CommentResponseDto
                .builder()
                .commentId(updateComments.getId())
                .result(SUCCESS_CODE)
                .build();
    }

    public CommentResponseDto deleteComment(Member member, long commentId)
            throws  IllegalStateException, NoSuchElementException {
        Comments deleteComments = commentRepository.findById(commentId)
                .filter(comments -> comments.getMember().getId() == member.getId())
                .map(comments -> {
                    commentRepository.delete(comments);
                    return comments;
                })
                .orElseThrow(NoCommentElementException::new);

        return CommentResponseDto
                .builder()
                .commentId(deleteComments.getId())
                .result(SUCCESS_CODE)
                .build();
    }
}
