package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.BoxOfficeRankingDto;
import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import com.codeworrisors.Movie_Community_Web.repository.BoxOfficeRankingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoxOfficeRankingService {

    private BoxOfficeRankingRepository boxOfficeRankingRepository;

    public BoxOfficeRankingService(BoxOfficeRankingRepository boxOfficeRankingRepository) {
        this.boxOfficeRankingRepository = boxOfficeRankingRepository;
    }

    public List<BoxOfficeRankingDto> getBoxOfficeRanking(){

        List<BoxOfficeRanking> rankingList = boxOfficeRankingRepository.findAllByTargetDt(LocalDate.now().minusDays(1));
        List<BoxOfficeRankingDto> rankingDTOList = new ArrayList<>();

        for( BoxOfficeRanking entity : rankingList){
            rankingDTOList.add( converToDTO(entity) );
        }

        return rankingDTOList;
    }

    private BoxOfficeRankingDto converToDTO(BoxOfficeRanking boxOfficeRanking){
        BoxOfficeRankingDto dto = new BoxOfficeRankingDto();
        dto.setRank(boxOfficeRanking.getRank());
        dto.setRankInten(boxOfficeRanking.getRankInten());
        dto.setMovieNm(boxOfficeRanking.getMovieTitle());
        return dto;
    }
}
