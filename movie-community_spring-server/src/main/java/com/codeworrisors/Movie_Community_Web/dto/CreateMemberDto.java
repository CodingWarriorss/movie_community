package com.codeworrisors.Movie_Community_Web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class CreateMemberDto {
    private String memberName;
    private String password;
    private String name;
    private String email;
    private String bio;
    private String website;
    private MultipartFile profileImg;
}
