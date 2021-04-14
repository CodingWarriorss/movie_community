package com.codeworrisors.Movie_Community_Web.dto.naver;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverAPIResponseDto {
    private String lastBuildDate;
    private int total;
    private List<NaverMovieDto> items;
}