package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Setter
@Getter
public class Review { // 리뷰 게시물

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private long id;
    private String movieTitle;
    private String content;
    @CreationTimestamp
    private Timestamp createDate;   // 작성일

    // 작성자
//    @ManyToOne(cascade = CascadeType.ALL) // [wrong] (여기서 1차 오류 ->  detached entity passed to persist... )
    @ManyToOne(cascade = CascadeType.MERGE) // 여러개의 게시물 - 한명의 작성자
    private Member member;
    // 이미지
//    @OneToMany(cascade = CascadeType.MERGE) // [wrong] (여기서 2차 오류 -> transient....)
    @OneToMany(cascade = CascadeType.ALL) // 하나의 리뷰 게시물 - 여러개의 이미지
    private List<Image> imageList;

    // 좋아요
    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> likes;

    // 댓글
}
