package com.codeworrisors.Movie_Community_Web.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateMemberDto {
    private String name;
    private String email;
    private String bio;
    private String website;
    private MultipartFile profileImg;
}
