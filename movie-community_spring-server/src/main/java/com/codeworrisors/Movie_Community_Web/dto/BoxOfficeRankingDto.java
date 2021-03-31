package com.codeworrisors.Movie_Community_Web.dto;

import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoxOfficeRankingDto {
    private Integer rank;
    private String movieNm;
    private String rankInten;
}
