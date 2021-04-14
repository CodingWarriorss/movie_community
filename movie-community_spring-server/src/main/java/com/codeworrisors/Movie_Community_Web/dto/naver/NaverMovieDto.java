package com.codeworrisors.Movie_Community_Web.dto.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverMovieDto {
    String title;
    String link;
    String image;
    String actor;
    String director;
    String userRating;
    String pupDate;
}
