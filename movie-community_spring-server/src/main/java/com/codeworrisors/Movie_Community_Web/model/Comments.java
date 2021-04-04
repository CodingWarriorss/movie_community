package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "comment_seq_gen",
        sequenceName = "comment_seq",
        allocationSize = 50
)
public class Comments {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_seq_gen"
    )
    private Long id;
    @NonNull
    @Setter
    private String content;
    @CreationTimestamp
    private Timestamp createDate;

    @NonNull
    @ManyToOne
    @JsonIgnoreProperties({"password", "name", "email", "address", "gender", "birth", "phone", "role"})
    private Member member;

    @NonNull
    @ManyToOne
    @JsonIgnore
    private Review review;
}
