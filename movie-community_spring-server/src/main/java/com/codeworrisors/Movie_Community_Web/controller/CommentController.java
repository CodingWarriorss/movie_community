package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.comment.response.CommentResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.comment.request.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.comment.request.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/review/comment", produces = "application/json")
public class CommentController {

    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponseDto postComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                          @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createComment(userDetail.getMember(), createCommentDto);

    }

    @PutMapping
    public CommentResponseDto modifyComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(userDetail.getMember(), updateCommentDto);
    }

    @DeleteMapping
    public CommentResponseDto deleteComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestParam long commentId) {
        return commentService.deleteComment(userDetail.getMember(), commentId);
    }
}
