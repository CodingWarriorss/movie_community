package com.codeworrisors.Movie_Community_Web.dto.member.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberUpdateResponseDto {
    private String result;
    private MemberResponseDto member;
}
