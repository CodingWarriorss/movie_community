package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter @ToString
@Table(name = "member")
@SequenceGenerator(
        name = "MEMBER_SEQ_GEN",
        sequenceName = "MEMBER_ID_SEQ",
        allocationSize = 1)
public class Member {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_ID_SEQ")
    private long id;
    private String memberName;
    private String password;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String birth;
    private String phone;
    private String role; // ROLE_USER

}
