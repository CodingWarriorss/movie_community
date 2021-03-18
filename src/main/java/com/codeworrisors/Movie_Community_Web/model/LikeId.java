package com.codeworrisors.Movie_Community_Web.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LikeId implements Serializable {
    @Column(name = "member_id")
    private long memberId;
    @Column(name = "review_id")
    private long reviewId;

}
