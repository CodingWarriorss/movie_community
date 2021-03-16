package com.codeworrisors.Movie_Community_Web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity

/**
 * 테이블명 및 컬럼의 설정
 * email은 아이디는 아니지만 유니크 해야하기 때문에 아래와 같은 설정으로 보입니다.
 * 자세한 기능은 다시 찾아보겠습니다.
 */
@Table(name = "members" , uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    //이메일로 검증을 했는가 설정하는 부분 그냥 있길레 안지웠습니다.
    @Column(nullable = false)
    private Boolean emailVerified = false;

    //json으로 생성시에 비밀번호는 안나오게 설정
    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
}
