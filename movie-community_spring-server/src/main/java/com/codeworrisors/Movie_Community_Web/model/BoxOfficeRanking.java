package com.codeworrisors.Movie_Community_Web.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "box_office_ranking")
@SequenceGenerator(name = "box_office_gen",
        sequenceName = "box_office_gen",
        allocationSize = 1)
@Getter
@NoArgsConstructor
public class BoxOfficeRanking {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_office_gen")
    private Long id;

    private String movieTitle;
    private Integer rank;
    private String rankInten;

    private LocalDate targetDt;

    public BoxOfficeRanking(String movieTitle, Integer rank, String rankInten, LocalDate targetDt) {
        this.movieTitle = movieTitle;
        this.rank = rank;
        this.rankInten = rankInten;
        this.targetDt = targetDt;
    }
}
