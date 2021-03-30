package com.codeworrisors.Movie_Community_Web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "REVIEW_SEQ_GEN",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Review {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "REVIEW_SEQ_GEN"
    )
    private long id;
    @NonNull
    private String movieTitle;
    @NonNull
    @Setter
    private String content;
    @NonNull
    @Setter
    private int rating;
    @CreationTimestamp
    private Timestamp createDate;

    @ManyToOne
    @NonNull
    @JsonIgnoreProperties({"password", "name", "email", "address", "gender", "birth", "phone", "role"})
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> imageList;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Likes> likesList;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comments> commentsList;

    @Setter
    @Transient
    private int likeCount;
}
