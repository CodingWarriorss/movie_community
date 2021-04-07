package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.service.CommentService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review/comment")
public class CommentController {
    public static final String RESULT = "result";
    public static final String SUCCESS = "success";
    public static final String COMMENT = "comment";
    public static final String FAIL = "fail";
    public static final String ERROR_DELIMITER = "/";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public JSONObject postComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                  @RequestBody CreateCommentDto createCommentDto) {
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        try {
            response.put(COMMENT,
                    commentService.createComment(userDetail.getMember(), createCommentDto));
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + ERROR_DELIMITER + e.getMessage());
        }

        return response;
    }

    @PutMapping
    public JSONObject modifyComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestBody UpdateCommentDto updateCommentDto) {
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        try {
            commentService.updateComment(userDetail.getMember(), updateCommentDto);
        } catch (IllegalStateException | NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + ERROR_DELIMITER + e.getMessage());
        }

        return response;
    }

    @DeleteMapping
    public JSONObject deleteComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestParam long commentId) {
        JSONObject response = new JSONObject();
        response.put(RESULT, SUCCESS);

        try {
            commentService.deleteComment(userDetail.getMember(), commentId);
        } catch (IllegalStateException | NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put(RESULT, FAIL + ERROR_DELIMITER + e.getMessage());
        }

        return response;
    }
}
