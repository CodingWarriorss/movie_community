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
        name = "likes_seq_gen",
        sequenceName = "likes_seq",
        allocationSize = 50
)
public class Likes {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "likes_seq_gen"
    )
    private Long id;

    @NonNull
    @ManyToOne
    @JsonIgnoreProperties({"password", "name", "email", "address", "gender", "birth", "phone", "role"})
    private Member member;

    @NonNull
    @ManyToOne
    @JsonIgnore
    private Review review;
}
