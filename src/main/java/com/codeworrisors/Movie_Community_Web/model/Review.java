package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_Sequence")
//    @SequenceGenerator(name = "review_Sequence", sequenceName = "REVIEW_SEQ")
    private long reviewId;

    @ManyToOne
    private Member member;
    private String content;
    private String movieTitle;
    private String imageUrl;
    @CreationTimestamp
    private Timestamp createDate;
}
