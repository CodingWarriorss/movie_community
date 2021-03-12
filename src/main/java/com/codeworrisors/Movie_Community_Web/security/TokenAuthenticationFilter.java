package com.codeworrisors.Movie_Community_Web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        /*
        *  저희가 만든 토큰으로 처리를 하고 난다음
        spring security에 설정된? 필터로 넘어가는가 아닐까합니다.
        */
        try{
            String jwt = getJwtFromRequest(request);

            if( StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) ){
                Long userId = tokenProvider.getUserIdFromToken(jwt);

                UserDetails userDetails = customMemberDetailsService.loadMemberById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities() );

                authentication.setDetails(userDetails);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch ( Exception ex ){
            //로그나 다른 처리를 하는걸로 보입니다
        }

        filterChain.doFilter(request, response);
        
    }

    //request에서 accessToken을 jwt에 맞게 변환하는듯... 구체적으로는 잘 모르겠습니다.
    private String getJwtFromRequest(HttpServletRequest request) {
        //StringUtils 같은 동일한 명칭의 클래스를 이곳저곳에서 써서 조금 짜증남.
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    
}
