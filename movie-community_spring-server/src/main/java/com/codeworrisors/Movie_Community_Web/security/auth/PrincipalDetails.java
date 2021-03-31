package com.codeworrisors.Movie_Community_Web.security.auth;

import com.codeworrisors.Movie_Community_Web.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
'/login' 요청이 오면 시큐리티가 낚아채서 로그인을 진행.
시큐리티는 `Security ContextHolder`라는 자신만의 session 공간을 가진다.
[세션] Security Session => [객체] Authentication 객체  => [객체] UserDetails 타입
*/

@Getter
@Setter
@NoArgsConstructor
public class PrincipalDetails implements UserDetails { // UserDetails를 구현했기 때문에 Authentication에 포함될 수 있음

    private Member member; // 컴포지션

    // 일반로그인시 사용하는 Constructor
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 권한: ROLE_USER
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> member.getRole());
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberName();
    }

    @Override
    public boolean isAccountNonExpired() { // 만료 여부 : true(No), false(Yes)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠김 여부 : true(No), false(Yes)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 기간 만료 여부 : true(No), false(Yes)
        return true;
    }

    /* 휴면계정 전환 설정시 사용하는 메서드
    User에 loginDate 필드를 두고 로그인할 때마다 갱신.
    if (현재시간 - user.getLoginDate()[로그인시간] > 1년) return false
    * */
    @Override
    public boolean isEnabled() { // 계정 활성화 여부: true(No), false(Yes)
        return true;
    }
}
