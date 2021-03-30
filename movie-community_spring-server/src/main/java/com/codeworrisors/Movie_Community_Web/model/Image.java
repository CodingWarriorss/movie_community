package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "IMAGE_SEQ_GEN",
        sequenceName = "IMAGE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Image {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "IMAGE_SEQ_GEN"
    )
    @Getter
    private long id;

    @NonNull
    @Getter
    private String fileName;

    @ManyToOne
    @NonNull
    @JsonIgnore
    private Review review;
}
