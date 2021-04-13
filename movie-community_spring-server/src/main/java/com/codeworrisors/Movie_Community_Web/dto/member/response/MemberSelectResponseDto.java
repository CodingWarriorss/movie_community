package com.codeworrisors.Movie_Community_Web.dto.member.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSelectResponseDto {
    private String name;
    private String email;
    private String website;
    private String bio;
    private String profileImg;
}
