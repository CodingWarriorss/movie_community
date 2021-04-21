package com.codeworrisors.Movie_Community_Web.dto.review.response;

import com.codeworrisors.Movie_Community_Web.model.Comments;
import lombok.Builder;
import lombok.Getter;

import javax.xml.stream.events.Comment;
import java.sql.Timestamp;

@Builder
@Getter
public class ReviewCommentResponseDto {
    private Long id;
    private ReviewMemberResponseDto member;
    private String content;
    private Timestamp createDate;

    public static ReviewCommentResponseDto fromComment(Comments comment) {
        return ReviewCommentResponseDto
                .builder()
                .id(comment.getId())
                .member(ReviewMemberResponseDto.fromMember(comment.getMember()))
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .build();
    }
}
