package com.codeworrisors.Movie_Community_Web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "image")
@SequenceGenerator(
        name = "IMAGE_ID_GEN",
        sequenceName = "IMAGE_ID_SEQ",
        allocationSize = 1)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_ID_SEQ")
    private int id;
    private String fileName;

    @ManyToOne(cascade = CascadeType.ALL) // 여러개의 이미지 - 하나의 리뷰 게시물
    private Review review;
}
