package com.codeworrisors.Movie_Community_Web.model;

import com.codeworrisors.Movie_Community_Web.exception.NoAuthReviewStateException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "review_seq_gen",
        sequenceName = "review_seq",
        allocationSize = 50
)
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_seq_gen"
    )
    private Long id;
    @NonNull
    private String movieTitle;
    @NonNull
    @Setter
    private String content;
    @NonNull
    @Setter
    private Integer rating;
    @CreationTimestamp
    private Timestamp createDate;
    @Setter
    @Transient
    private int likeCount;
    @Setter
    @Transient
    private int commentCount;

    @NonNull
    @ManyToOne
    @Setter
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> imageList;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Likes> likesList;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comments> commentsList;

    public Review updateReview(Long memberId, String content, int rating) {
        validateReviewAuth(memberId);
        this.content = content;
        this.rating = rating;
        return this;
    }

    public Review validateReviewAuth(Long memberId) {
        if (member.isNotSameMember(memberId)) {
            throw new NoAuthReviewStateException();
        }
        return this;
    }
}
