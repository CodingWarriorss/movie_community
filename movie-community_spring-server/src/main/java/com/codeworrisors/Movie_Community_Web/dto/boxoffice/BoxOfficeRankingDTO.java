package com.codeworrisors.Movie_Community_Web.dto.boxoffice;

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
public class BoxOfficeRankingDTO {
    private Integer rank;
    private String movieNm;
    private String rankInten;


    //과연 이게 좋은건지는 다시 생각해봅시다. 하지말라는데는 이유가 있겠죠...
    public BoxOfficeRanking convertToEntity(){
        return new BoxOfficeRanking( this.movieNm, this.rank, this.rankInten, LocalDate.now().minusDays(1) );
    }

    public void setToDTO( BoxOfficeRanking boxOfficeRanking){
        this.rank = boxOfficeRanking.getRank();
        this.movieNm = boxOfficeRanking.getMovieTitle();
        this.rankInten = boxOfficeRanking.getRankInten();
    }

}
