package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Setter @Getter
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_Sequence")
    @SequenceGenerator(name = "review_Sequence", sequenceName = "REVIEW_SEQ")
    int reviewId;
    @ManyToOne
    Member member;
    String content;
    String movieTitle;
    String imageUrl;
    @CreationTimestamp
    private Timestamp createDate;
}
