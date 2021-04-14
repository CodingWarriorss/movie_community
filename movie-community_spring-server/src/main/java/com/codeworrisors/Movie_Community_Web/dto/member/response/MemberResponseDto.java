package com.codeworrisors.Movie_Community_Web.dto.member.response;

import com.codeworrisors.Movie_Community_Web.model.AuthProvider;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponseDto {
    private String name;
    private String email;
    private String website;
    private String bio;
    private String profileImg;
    private String memberName;
    private AuthProvider provider;
}
