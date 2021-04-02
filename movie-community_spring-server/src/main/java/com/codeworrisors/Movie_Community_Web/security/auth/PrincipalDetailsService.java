package com.codeworrisors.Movie_Community_Web.security.auth;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 `/login` 요청이 오면 UserDetailsService의 loadByUsername()이 호출된다.
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName)
            throws UsernameNotFoundException {
        Optional<Member> byMemberName = memberRepository.findByMemberName(memberName);

        if (byMemberName.isPresent()) {
            return new PrincipalDetails(byMemberName.get());
        }
        logger.error("아이디 '" + memberName + "'가 존재하지 않음");
        return null;
    }
}
