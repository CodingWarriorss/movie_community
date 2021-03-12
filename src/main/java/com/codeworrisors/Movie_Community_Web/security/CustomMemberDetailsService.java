package com.codeworrisors.Movie_Community_Web.security;

import com.codeworrisors.Movie_Community_Web.exception.ResourceNotFoundException;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailsService implements UserDetailsService{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow( () ->
                new UsernameNotFoundException("사용자의 Email을 찾지 못하였습니다.")
        );
        return UserPrincipal.create(member);
    }

    public UserDetails loadMemberById(Long id){
        Member member = memberRepository.findById(id)
            .orElseThrow( () -> 
                new ResourceNotFoundException("Member" , "가입이 안된 회원입니다.", id)
            );

        return UserPrincipal.create(member);
    }
    
    
}
