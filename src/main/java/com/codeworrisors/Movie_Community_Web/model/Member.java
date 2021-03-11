package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@ToString
@Entity
public class Member {

    @Builder
    public Member(String memberName, String password, String name, String email, String address, String gender, String birth, String phone, String role) {
        this.memberName = memberName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
    }

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
