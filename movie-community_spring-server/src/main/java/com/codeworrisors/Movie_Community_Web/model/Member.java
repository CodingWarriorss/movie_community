package com.codeworrisors.Movie_Community_Web.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    public Member(String memberName, String password, String name, String email,
                  String bio, String website, String profileImg, RoleType role) {
        this.memberName = memberName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.website = website;
        this.profileImg = profileImg;
        this.role = role;
    }

    public boolean isNotSameMember(Long id) {
        return !this.id.equals(id);
    }

    public String changeName(String name) {
        return this.name = name;
    }

    public String changeEmail(String email) {
        return this.email = email;
    }

    public String changeWebsite(String website) {
        return this.website = website;
    }

    public String changeBio(String bio) {
        return this.bio = bio;
    }

    public String changeProfile(MultipartFile profileImg) {
        if (profileImg != null) {
            return this.profileImg = profileImg.getName();
        }
        return this.profileImg;
    }
}
