package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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

//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Review> uploadedReviews;
}
