package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "LIKES_SEQ_GEN",
        sequenceName = "LIKES_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Likes {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LIKES_SEQ_GEN"
    )
    private long id;

    @ManyToOne @NonNull
    @JsonIgnoreProperties({"password", "name", "email", "address", "gender", "birth", "phone", "role"})
    private Member member;

    @ManyToOne @NonNull
    @JsonIgnore
    private Review review;
}
