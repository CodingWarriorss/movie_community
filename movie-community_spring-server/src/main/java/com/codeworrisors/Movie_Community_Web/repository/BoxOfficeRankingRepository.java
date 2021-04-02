package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;

public interface BoxOfficeRankingRepository extends JpaRepository<BoxOfficeRanking, Long> {
    List<BoxOfficeRanking> findAllByTargetDt(LocalDate targetDate);
}
