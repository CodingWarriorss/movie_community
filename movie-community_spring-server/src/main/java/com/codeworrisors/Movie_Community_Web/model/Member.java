package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GEN",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class Member {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GEN"
    )
    private long id;
    @Setter
    private String memberName;
    @Setter
    private String password;
    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String address;
    @Setter
    private String gender;
    @Setter
    private String birth;
    @Setter
    private String phone;
    @Setter
    private String role;
}
