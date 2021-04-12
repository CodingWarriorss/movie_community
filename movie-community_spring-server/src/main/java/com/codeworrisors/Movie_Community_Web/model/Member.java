package com.codeworrisors.Movie_Community_Web.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "member_seq_gen",
        sequenceName = "member_seq",
        allocationSize = 50
)
public class Member {

    @Id
    @Setter
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_seq_gen"
    )
    private Long id;
    @Setter
    private String memberName;
    @Setter
    private String password;
    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String bio;
    @Setter
    private String website;
    @Setter
    private String profileImg;
    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public boolean isNotSameMember(Long id) {
        return !this.id.equals(id);
    }
}
