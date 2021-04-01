package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "image_seq_gen",
        sequenceName = "image_seq",
        allocationSize = 50
)
public class Image {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_seq_gen"
    )
    @Getter
    private long id;
    @NonNull
    @Getter
    private String fileName;

    @NonNull
    @ManyToOne
    @JsonIgnore
    private Review review;
}
