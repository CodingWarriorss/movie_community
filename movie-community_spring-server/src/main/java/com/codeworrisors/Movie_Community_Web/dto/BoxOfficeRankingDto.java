package com.codeworrisors.Movie_Community_Web.dto;

import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class BoxOfficeRankingDto {
    private Integer rank;
    private String movieNm;
    private String rankInten;

    public BoxOfficeRanking convertToEntity(){
        return new BoxOfficeRanking( this.movieNm, this.rank, this.rankInten, LocalDate.now().minusDays(1) );
    }

    public void setToDTO( BoxOfficeRanking boxOfficeRanking){
        this.rank = boxOfficeRanking.getRank();
        this.movieNm = boxOfficeRanking.getMovieTitle();
        this.rankInten = boxOfficeRanking.getRankInten();
    }

}
