package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@Table(name = "image")
@SequenceGenerator(
        name = "IMAGE_ID_GEN",
        sequenceName = "IMAGE_ID_SEQ")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_ID_SEQ")
    @Column(name = "id")
    private int id;
    private String fileName;

    @ManyToOne // 여러개의 이미지 - 하나의 리뷰 게시물
    @JsonBackReference
    private Review review;
}
