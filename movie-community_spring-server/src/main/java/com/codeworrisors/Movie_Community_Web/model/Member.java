package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "member_seq_gen",
        sequenceName = "member_seq",
        allocationSize = 50
)
public class Member {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_seq_gen"
    )
    private Long id;
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
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
