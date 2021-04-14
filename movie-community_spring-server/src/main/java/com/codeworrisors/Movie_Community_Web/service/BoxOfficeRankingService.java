package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.boxoffice.BoxOfficeRankingDto;
import com.codeworrisors.Movie_Community_Web.repository.BoxOfficeRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoxOfficeRankingService {

    private final BoxOfficeRankingRepository boxOfficeRankingRepository;

    public List<BoxOfficeRankingDto> getBoxOfficeRanking() {
        List<BoxOfficeRankingDto> rankingDTOList = new ArrayList<>();
        boxOfficeRankingRepository.findAllByTargetDt(LocalDate.now().minusDays(1))
                .forEach(boxOfficeRanking -> {
                    rankingDTOList.add(new BoxOfficeRankingDto(boxOfficeRanking.getRank(), boxOfficeRanking.getMovieTitle(), boxOfficeRanking.getRankInten()));
                });
        return rankingDTOList;
    }
}
