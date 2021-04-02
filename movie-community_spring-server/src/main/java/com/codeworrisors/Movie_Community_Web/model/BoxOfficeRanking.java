package com.codeworrisors.Movie_Community_Web.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(name = "box_office_gen",
        sequenceName = "box_office_gen",
        allocationSize = 50)
public class BoxOfficeRanking {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "box_office_gen")
    private Long id;
    @NonNull
    private String movieTitle;
    @NonNull
    private Integer rank;
    @NonNull
    private String rankInten;
    @NonNull
    private LocalDate targetDt;
}
