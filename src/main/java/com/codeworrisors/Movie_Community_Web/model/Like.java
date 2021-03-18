package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "likes")
public class Like {
    @EmbeddedId
    private LikeId likeId;

    @ManyToOne()
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    private Review review;
}
