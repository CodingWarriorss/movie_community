package com.codeworrisors.Movie_Community_Web.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

//Review 등록시 Request 맵핑? 모델 클래스?
@Getter
@Setter
public class ReviewRequest {
    private String movieTitle;
    private String content;
    private int rating;
    private List<MultipartFile> files;

    //파일 리스트 null check
    public boolean isFilesNotNull(){
        return ( files != null );
    }
}
