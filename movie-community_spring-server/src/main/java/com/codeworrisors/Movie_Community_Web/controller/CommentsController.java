package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.dto.CreateCommentDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateCommentDto;
import com.codeworrisors.Movie_Community_Web.service.CommentService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/review")
public class CommentsController {
    static final String RESULT = "result";
    static final String COMMENT = "comment";
    static final String SUCCESS = "SUCCESS";
    static final String FAIL = "FAIL";

    private final CommentService commentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    /*
     * 댓글 생성
     * response(성공/실패 여부, 만들어진 commentId)
     * */
    @PostMapping("/comment")
    public JSONObject postComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                  @RequestBody CreateCommentDto createCommentDto) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            response.put(COMMENT, commentService.createComment(userDetail.getMember(), createCommentDto));
        } catch (EntityNotFoundException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 리뷰에 대한 댓글 추가 요청");
        }

        return response;
    }

    /*
     * 댓글 수정 => POSTMAN에서만 해봄
     * response(성공/실패 여부)
     * */
    @PutMapping("/comment")
    public JSONObject modifyComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    UpdateCommentDto updateCommentDto) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESULT, SUCCESS);
            commentService.updateComment(userDetail.getMember(), updateCommentDto);
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 댓글에 대한 수정 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 댓글에 대한 수정 요청");
        }

        return response;
    }

    /*
     * 댓글 삭제 => POSTMAN에서만 해봄
     * response(성공/실패 여부)
     * */
    @DeleteMapping("/comment")
    public JSONObject deleteComment(@AuthenticationPrincipal PrincipalDetails userDetail,
                                    @RequestParam long commentId) {
        JSONObject response = new JSONObject();

        logger.info("===========comment delete start=============");
        logger.info("delete ID : " + commentId);


        try {
            response.put(RESULT, SUCCESS);
            commentService.deleteComment(userDetail.getMember(), commentId);
        } catch (IllegalStateException e) {
            response.put(RESULT, FAIL + "/권한 없는 댓글에 대한 삭제 요청");
        } catch (NoSuchElementException e) {
            response.put(RESULT, FAIL + "/존재하지 않는 댓글에 대한 삭제 요청");
        }

        return response;
    }
}
