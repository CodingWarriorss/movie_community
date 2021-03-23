package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@Table(name = "comment")
@SequenceGenerator(name = "COMMENT_ID_GEN",
                    sequenceName = "COMMENT_ID_SEQ",
                    allocationSize = 1)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_ID_GEN")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private String content;

    @CreationTimestamp
    private Timestamp createDate;
}
