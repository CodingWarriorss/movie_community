package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@Table(name = "likes")
@SequenceGenerator(
        name = "LIKE_ID_GEN",
        sequenceName = "LIKE_ID_SEQ",
        allocationSize = 1)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_ID_GEN")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Likes(Member member, Review review) {
        this.member = member;
        this.review = review;
    }
}
