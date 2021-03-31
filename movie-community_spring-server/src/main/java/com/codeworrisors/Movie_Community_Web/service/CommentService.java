package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.model.Comments;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.CommentRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    public CommentService(ReviewRepository reviewRepository, CommentRepository commentRepository) {
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
    }


    /*
     * CREATE, UPDATE, DELETE COMMENT
     * */
    public JSONObject createComment(Member member, CreateCommentDto createCommentDto) throws EntityNotFoundException {
        Comments saved = commentRepository.save(
                new Comments(createCommentDto.getContent(), member, reviewRepository.getOne(createCommentDto.getReviewId())));

        JSONObject result = new JSONObject();
        result.put("commentId", saved.getId());
        return result;
    }

    public JSONObject updateComment(Member member, UpdateCommentDto updateCommentDto) throws IllegalStateException, NoSuchElementException {
        JSONObject result = new JSONObject();

        commentRepository.findById(updateCommentDto.getCommentId())
                .ifPresentOrElse(comment -> {
                    if (member.getId() != comment.getMember().getId())
                        throw new IllegalStateException("권한 없는 댓글에 대한 수정 요청");
                    comment.setContent(updateCommentDto.getContent());
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 댓글에 대한 수정 요청");
                });

        return result;
    }

    public void deleteComment(Member member, long commentId) throws IllegalStateException, NoSuchElementException {
        commentRepository.findById(commentId)
                .ifPresentOrElse(comment -> {
                    if (member.getId() != comment.getMember().getId())
                        throw new IllegalStateException("권한 없는 댓글에 대한 삭제 요청");
                    commentRepository.delete(comment);
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 댓글에 대한 삭제 요청");
                });
    }

//
//    public void postComment(Comments comments) {
//        commentRepository.save(comments);
//    }
//
//    public void deleteComment(Comments comments) {
//        commentRepository.delete(comments);
//    }
//
//    public Comments findCommentsById(Long commentsId) {
//        try {
//            return commentRepository.findById(commentsId).get();
//        } catch (NoSuchElementException e) {
//            return null;
//        }
//
//    }
//
//    public List<Comments> findCommentsByReviewId(Review review) {
//        return commentRepository.findAllByReview(review);
//    }
}
