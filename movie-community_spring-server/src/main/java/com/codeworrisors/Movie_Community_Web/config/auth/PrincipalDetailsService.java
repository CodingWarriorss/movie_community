package com.codeworrisors.Movie_Community_Web.config.auth;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// `/login` 요청이 오면 UserDetailsService의 loadByUsername()이 호출된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    // loadUserByUsername() 종료 => @AuthenticationPrincipal 생성됨
    @Override
    public UserDetails loadUserByUsername(String memberName)
            throws UsernameNotFoundException {
        System.out.println("loadUserByUsername :" + memberName);
        Optional<Member> byMemberName = memberRepository.findByMemberName(memberName);

        if (byMemberName.isPresent()){
            return new PrincipalDetails(byMemberName.get());
        } else{
            System.out.println("Not Found 'memberName'");
        }
        return null;
    }
}
