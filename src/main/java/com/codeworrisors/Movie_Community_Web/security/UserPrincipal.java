package com.codeworrisors.Movie_Community_Web.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.codeworrisors.Movie_Community_Web.model.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * OAuth2User는 OAuth2인증 관련한여 사용
 * UserDetails는 SpringSecurity에서 사용하기 위해
 * 다중상속을 ? 구현 
 */
public class UserPrincipal implements OAuth2User, UserDetails{

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    public static UserPrincipal create(Member member){
        /**
         * 여기서는 권한을 설정하는 부분으로 
         * 아마다 default로 유저의 권한 하나만 있는 것으로 제작한것으로 보입니다.
         */
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(
            member.getId(),
            member.getEmail(),
            member.getPassword(),
            authorities
    );
    }

    public static UserPrincipal create(Member member, Map<String, Object> attributes){
        UserPrincipal userPrincipal = UserPrincipal.create(member);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
    
   
    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    
}
