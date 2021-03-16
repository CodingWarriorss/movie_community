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
//@ToString
public class Review { // 리뷰 게시물

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String movieTitle;
    private String content;
    @CreationTimestamp
    private Timestamp createDate;   // 작성일

    // 작성자
    @ManyToOne // 여러개의 게시물 - 한명의 작성자
    @JoinColumn(name = "member_id")
    private Member member;
    // 이미지
    // 좋아요
    // 댓글
}
