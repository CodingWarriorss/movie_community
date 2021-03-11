package com.codeworrisors.Movie_Community_Web.config;

import com.codeworrisors.Movie_Community_Web.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration //IoC
@EnableWebSecurity // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 쿠키와 세션 사용 안함

                .and()
                .addFilter(corsFilter) // 리소스공유 설정 필터 주입

                .formLogin().disable() // 폼 로그인 방식 사용 안함
                .httpBasic().disable() // http basic 방식 사용 안함.

                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // 로그인 인증(Authentication) 필터 주입[,token]

                .authorizeRequests() // Bearer 방식 사용 : `Authorization : jwt(ID,PWD 암호화값)`
//                .antMatchers("/api/members/**")
//                .access("hasRole('ROLE_USER')")
                .anyRequest()
                .permitAll();
    }
}
