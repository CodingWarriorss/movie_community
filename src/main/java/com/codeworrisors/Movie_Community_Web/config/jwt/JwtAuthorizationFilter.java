package com.codeworrisors.Movie_Community_Web.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
클라이언트가 권한/인증이 필요한 특정주소를 요청하면
BasicAuthenticationFilter를 거쳐 인가된 사용자인지 확인한다.
만일 권한/인증이 필요하지 않은 주소라면 해당 필터는 작동하지 않는다.
*/

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("클라이언트가 인증/권한이 필요한 주소를 요청");

        // 1. Request Header를 확인
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if (jwtHeader == null || ! jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
        }

        // 2. JWT 토큰 검증 : 권한 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        String memberName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build() // 암호화
                .verify(jwtToken)
                .getClaim("memberName").asString(); // JwtAuthenticationFilter에서 claim에 삽입한 값

        // 3. 서명이 정상
        if (memberName != null) {
            System.out.println("서명이 정상처리되었음");

            // 3-1) 임의로 authentication 객체를 생성
            Member memberEntity = memberRepository.findByMemberName(memberName).get();
            PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails,
                            null, principalDetails.getAuthorities());

            // 3-2) 시큐리티 세션에 강제 주입
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 프로세스 진행
            chain.doFilter(request, response);
        }
    }
}
