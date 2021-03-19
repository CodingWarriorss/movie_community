package com.codeworrisors.Movie_Community_Web.config.jwt;

/*


 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // `/login` 요청시 로그인 시도를 위해 실행되는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        System.out.println("==================로그인 처리 중====================");
        // 1. JSON 파싱해서 membername, password 받기
        Member member = null;
        ObjectMapper om = new ObjectMapper();
        try {
            member = om.readValue(request.getInputStream(), Member.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("From Client : " + member); // 클라이언트로부터 받아온 로그인 정보

        // 2. Token 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(member.getMemberName(), member.getPassword());

        // 3. 생성된 Token을 이용하여 인증시도
        // => DB의 membername과 password 비교 => 정상 인증 => return authentication
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken); // UserDetailsService로 ㄱ
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();// 4. (test) 반환 결과 확인
        }
        catch (BadCredentialsException e) {
            System.out.println("[로그인 실패] 비밀번호가 틀림: " + member.getMemberName());
            response.addHeader(JwtProperties.HEADER_STRING, "failure/password");
        }
        catch (AuthenticationException e) {
            System.out.println("[로그인 실패] 아이디가 없음: " + member.getMemberName());
            response.addHeader(JwtProperties.HEADER_STRING, "failure/memberName");
        }

        // 5. 시큐리티 세션에 담기 (선택가능: 권한관리 위함)
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        System.out.println("[로그인 완료] 로그인 정보 : " + principalDetails.getMember()); // 값이 존재하면 로그인이 정상적으로 된 것

        // Hash 암호방식으로 암호화 (RSA(X), HMAC(O): 서버만 알고 있는 secret 값으로 암호화)
        String jwtToken = JWT.create()
                .withSubject("movie") // 토큰명
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 만료시간
                .withClaim("id", principalDetails.getMember().getId()) // 비공개 claim (인증에 사용할 key와 value 삽입)
                .withClaim("memberName", principalDetails.getMember().getMemberName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));  // secret : 서버만 알고 있는 고유 값(cw)

        // Authorization : Bearer + ' ' + jwtToken
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}