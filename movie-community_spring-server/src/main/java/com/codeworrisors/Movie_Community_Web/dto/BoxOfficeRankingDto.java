package com.codeworrisors.Movie_Community_Web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BoxOfficeRankingDto {
    private Integer rank;
    private String movieNm;
    private String rankInten;
}
