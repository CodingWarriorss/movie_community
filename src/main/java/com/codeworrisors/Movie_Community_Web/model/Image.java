package com.codeworrisors.Movie_Community_Web.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fileName;

    @ManyToOne // 여러개의 이미지 - 하나의 리뷰 게시물
    private Review review;
}
