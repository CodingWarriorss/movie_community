package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "member")
public class Member {

    @Id
    private String memberId;

    private String memberPassword;

    private String memberName;

    private String memberEmail;

    private String memberAddress;

    private String memberGender;

    private String memberBirth;

    private String memberPhone;

    private String memberRole; // ROLE_USER
}
