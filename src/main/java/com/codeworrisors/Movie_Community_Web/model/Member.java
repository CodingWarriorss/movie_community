package com.codeworrisors.Movie_Community_Web.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
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
}
