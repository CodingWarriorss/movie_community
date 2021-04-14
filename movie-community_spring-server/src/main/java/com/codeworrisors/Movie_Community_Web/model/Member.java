package com.codeworrisors.Movie_Community_Web.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", website='" + website + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider = AuthProvider.local;

    @Getter
    @Setter
    private String providerId;

    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.ROLE_USER;
}
