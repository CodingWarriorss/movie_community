package com.codeworrisors.Movie_Community_Web.security.auth;

import com.codeworrisors.Movie_Community_Web.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class PrincipalDetails implements UserDetails {

    private Member member;
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 권한: ROLE_USER
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> String.valueOf(member.getRole()));
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
