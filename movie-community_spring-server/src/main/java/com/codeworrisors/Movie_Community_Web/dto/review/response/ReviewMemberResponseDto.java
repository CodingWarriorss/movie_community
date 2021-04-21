package com.codeworrisors.Movie_Community_Web.dto.review.response;

import com.codeworrisors.Movie_Community_Web.model.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewMemberResponseDto {
    private Long id;
    private String memberName;
    private String profileImg;

    public static ReviewMemberResponseDto fromMember (Member member) {
        return ReviewMemberResponseDto.builder()
                .id(member.getId())
                .memberName(member.getMemberName())
                .profileImg(member.getProfileImg())
                .build();
    }
}
