package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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
